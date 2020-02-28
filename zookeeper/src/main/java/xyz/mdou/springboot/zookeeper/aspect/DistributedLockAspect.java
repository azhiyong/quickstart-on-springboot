package xyz.mdou.springboot.zookeeper.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import xyz.mdou.springboot.zookeeper.annotation.DistributedLock;
import xyz.mdou.springboot.zookeeper.annotation.DistributedLockField;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

@Slf4j
@Aspect
@Component
public class DistributedLockAspect {

    private static final String LOCK_PREFIX = "DISTRIBUTED_LOCK_";

    private static final String PATH_SEPARATOR = "/";

    private CuratorFramework client;

    @Autowired
    public DistributedLockAspect(CuratorFramework client) {
        this.client = client;
    }

    @Pointcut("@annotation(xyz.mdou.springboot.zookeeper.annotation.DistributedLock)")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] args = joinPoint.getArgs();
        DistributedLock lockAnnotation = method.getAnnotation(DistributedLock.class);
        if (StringUtils.isEmpty(lockAnnotation.key())) {
            throw new RuntimeException("DistributedLock key cannot be null");
        }
        String lockPath = buildLockPath(lockAnnotation, method, args);
        InterProcessMutex lock = new InterProcessMutex(client, lockPath);
        try {
            if (lock.acquire(lockAnnotation.timeout(), lockAnnotation.timeunit())) {
                return joinPoint.proceed();
            }
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        } finally {
            lock.release();
        }
        return null;
    }

    private String buildLockPath(DistributedLock lock, Method method, Object[] args)
            throws NoSuchFieldException, IllegalAccessException {
        StringBuilder lockPath = new StringBuilder(PATH_SEPARATOR + LOCK_PREFIX).append(lock.key());
        Annotation[][] annotations = method.getParameterAnnotations();
        for (int i = 0; i < annotations.length; i++) {
            for (Annotation annotation : annotations[i]) {
                if (!annotation.annotationType().isInstance(DistributedLockField.class)) {
                    continue;
                }
                Object parameter = args[i];
                String[] fields = ((DistributedLockField) annotation).fields();
                if (Arrays.isNullOrEmpty(fields)) {
                    if (Objects.isNull(parameter)) {
                        throw new RuntimeException("Parameter with DistributedLockField annotation cannot be null");
                    }
                    lockPath.append(PATH_SEPARATOR).append(parameter);
                } else {
                    Class<?> parameterClass = parameter.getClass();
                    for (String field : fields) {
                        Field declaredField = parameterClass.getDeclaredField(field);
                        declaredField.setAccessible(true);
                        lockPath.append(PATH_SEPARATOR).append(declaredField.get(parameter));
                    }
                }
            }
        }
        return lockPath.toString();
    }
}
