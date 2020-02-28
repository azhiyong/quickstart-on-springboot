package xyz.mdou.springboot.zookeeper.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DistributedLockField {

    String[] fields();
}
