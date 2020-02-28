package xyz.mdou.springboot.zookeeper.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DistributedLock {

    String key();

    long timeout() default 3000;

    TimeUnit timeunit() default TimeUnit.MILLISECONDS;
}
