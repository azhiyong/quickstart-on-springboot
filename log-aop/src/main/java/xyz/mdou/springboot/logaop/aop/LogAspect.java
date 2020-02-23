package xyz.mdou.springboot.logaop.aop;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

@Component
@Aspect
@Slf4j
public class LogAspect {
    private static final Gson GSON = new Gson();

    private static final String REQUEST_START_TIME = "requestStartTime";

    @Pointcut("execution(public * xyz.mdou.springboot.logaop.controller.*Controller.*(..))")
    public void pointcut() {

    }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        Map<String, String[]> parameterMap = request.getParameterMap();
        log.info("request {} {} {}", request.getMethod(), request.getRequestURL(),
                GSON.toJson(parameterMap));
        Signature signature = joinPoint.getSignature();
        log.info("{}.{}", signature.getDeclaringTypeName(), signature.getName());
        long startTime = System.currentTimeMillis();
        request.setAttribute(REQUEST_START_TIME, startTime);
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object o = joinPoint.proceed();
        log.info("proceed result {}", GSON.toJson(o));
        return o;
    }

    @AfterReturning("pointcut()")
    public void after() {
        ServletRequestAttributes attributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

        long startTime = (long) request.getAttribute(REQUEST_START_TIME);
        long endTime = System.currentTimeMillis();
        log.info("request cost {}ms", endTime - startTime);
    }
}
