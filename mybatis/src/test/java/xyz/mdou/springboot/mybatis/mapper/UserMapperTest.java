package xyz.mdou.springboot.mybatis.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.mdou.springboot.mybatis.MybatisApplicationTest;
import xyz.mdou.springboot.mybatis.entity.UserEntity;

import java.util.Date;
import java.util.List;

@Slf4j
public class UserMapperTest extends MybatisApplicationTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSave() {
        List<UserEntity> userEntities = userMapper.findAll();
        if (userEntities.size() == 0) {
            UserEntity user1 = new UserEntity().setName("user1").setBirthday(new Date());
            UserEntity user2 = new UserEntity().setName("user2").setBirthday(new Date());
            UserEntity user3 = new UserEntity().setName("user3").setBirthday(new Date());
            userEntities.add(user1);
            userEntities.add(user2);
            userEntities.add(user3);
            userMapper.saveAll(userEntities);
        }
        log.info("all user: {}", userMapper.findAll());
    }

    @Test
    public void testGetOne() {
        UserEntity user = userMapper.getOne(1L);
        log.info("getOne user: {}", user);
    }
}