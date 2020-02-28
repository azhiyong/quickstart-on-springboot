package xyz.mdou.springboot.redis.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.mdou.springboot.redis.RedisApplicationTest;
import xyz.mdou.springboot.redis.entity.UserEntity;

import java.util.Date;

@Slf4j
public class UserServiceTest extends RedisApplicationTest {

    @Autowired
    private UserService userService;

    @Test
    public void testSaveOrUpdate() {
        UserEntity user = new UserEntity().setUid(8L).setName("user8").setBirthday(new Date());
        userService.saveOrUpdate(user);
    }

    @Test
    public void testGetByUid() {
        UserEntity user1 = userService.getByUid(1L);
        log.info("test get user {} {}", 1L, user1);
        user1 = userService.getByUid(1L);
        log.info("test get user {} {}", 1L, user1);

        UserEntity user8 = userService.getByUid(8L);
        log.info("test get user {} {}", 8L, user8);
    }
}