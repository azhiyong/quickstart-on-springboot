package xyz.mdou.springboot.mybatisplus.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.mdou.springboot.mybatisplus.MybatisPlusApplicationTest;
import xyz.mdou.springboot.mybatisplus.entity.UserEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class UserServiceImplTest extends MybatisPlusApplicationTest {

    @Autowired
    private UserService userService;

    @Test
    public void testSave() {
        List<UserEntity> userEntities = new ArrayList<>();
        UserEntity user1 = new UserEntity().setName("user1").setBirthday(new Date());
        userService.save(user1);
        UserEntity user2 = new UserEntity().setName("user2").setBirthday(new Date());
        UserEntity user3 = new UserEntity().setName("user3").setBirthday(new Date());
        userEntities.add(user2);
        userEntities.add(user3);
        userService.saveBatch(userEntities);

        log.info("all user: {}", userService.list());
    }

    @Test
    public void testGetOne() {
        UserEntity user = userService.getById(1L);
        log.info("getOne user: {}", user);
    }
}