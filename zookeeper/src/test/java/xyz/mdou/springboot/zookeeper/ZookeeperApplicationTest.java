package xyz.mdou.springboot.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.mdou.springboot.zookeeper.annotation.DistributedLock;
import xyz.mdou.springboot.zookeeper.aspect.DistributedLockAspect;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZookeeperApplication.class)
public class ZookeeperApplicationTest {

    private Integer count = 10000;
    private ExecutorService executorService = Executors.newFixedThreadPool(100);

    @Autowired
    private CuratorFramework client;

    @Test
    public void testNonLock() throws InterruptedException {
        IntStream.range(0, 10000).forEach(i -> executorService.execute(this::decr));
        TimeUnit.MINUTES.sleep(1);
        log.info("testNonLock after 1 minute, count {}", count);
    }

    @Test
    public void testAopLock() throws InterruptedException {
        ZookeeperApplicationTest applicationTest = new ZookeeperApplicationTest();
        AspectJProxyFactory proxyFactory = new AspectJProxyFactory(applicationTest);
        proxyFactory.addAspect(new DistributedLockAspect(client));
        ZookeeperApplicationTest proxyApplication = proxyFactory.getProxy();
        IntStream.range(0, 10000).forEach(i -> executorService.execute(() -> proxyApplication.aopDecr(i)));
        TimeUnit.MINUTES.sleep(1);
        log.info("testAopLock after 1 minute, count {}", count);
    }

    @Test
    public void testLock() throws InterruptedException {
        IntStream.range(0, 10000).forEach(i -> executorService.execute(this::lockAndDecr));
        TimeUnit.MINUTES.sleep(1);
        log.info("testLock after 1 minute, count {}", count);
    }

    @DistributedLock(key = "test")
    public void aopDecr(int requestId) {
        log.info("aop test {}", requestId);
        decr();
    }

    private void lockAndDecr() {
        String lockPath = "/test";
        try {
            InterProcessMutex lock = new InterProcessMutex(client, lockPath);
            try {
                if (lock.acquire(3, TimeUnit.SECONDS)) {
                    decr();
                }
            } finally {
                lock.release();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void decr() {
        count -= 1;
        log.info("after decr, count {}", count);
    }
}