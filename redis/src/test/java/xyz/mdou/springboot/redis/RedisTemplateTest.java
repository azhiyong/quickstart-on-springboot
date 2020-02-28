package xyz.mdou.springboot.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import xyz.mdou.springboot.redis.entity.UserEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@Slf4j
public class RedisTemplateTest extends RedisApplicationTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String, Serializable> serializableRedisTemplate;

    @Test
    public void testRedisTemplate() {
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        IntStream.range(1, 1000)
                .forEach(i -> executorService.execute(
                        () -> stringRedisTemplate.opsForValue()
                                .increment("count", 1L)));
        stringRedisTemplate.opsForValue().set("k1", "k1");
        Assert.assertEquals("k1", stringRedisTemplate.opsForValue().get("k1"));

        UserEntity user = new UserEntity().setUid(8L).setName("user8").setBirthday(new Date());
        String key = "user:" + user.getUid();
        serializableRedisTemplate.opsForValue().set(key, user);

        log.info("cache user {}", serializableRedisTemplate.opsForValue().get(key));
    }
}
