package xyz.mdou.springboot.jdbctemplate.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.mdou.springboot.jdbctemplate.JdbcTemplateApplicationTest;
import xyz.mdou.springboot.jdbctemplate.entity.UserEntity;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
public class UserDaoTest extends JdbcTemplateApplicationTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void testSave() {
        UserEntity userEntity = new UserEntity()
                .setName("user1")
                .setBirthday(Date.from(Instant.from(LocalDate.of(1990, 3, 5)
                        .atStartOfDay(ZoneId.systemDefault()))))
                .setCreateDate(new Date())
                .setUpdateDate(new Date());
        log.info("user save {}", userDao.save(userEntity));
    }

    @Test
    public void testFindAll() {
        log.info("all user {}", userDao.findAll());
    }

    @Test
    public void testUpdate() {
        UserEntity userEntity = userDao.getOne(1L);
        if (userEntity != null) {
            userEntity.setName(userEntity.getName() + " uup");
            log.info("update user {}", userDao.update(1L, userEntity));
        }
    }

    @Test
    public void testDelete() {
        List<UserEntity> userEntities = userDao.findAll();
        Long uid = Optional.ofNullable(userEntities).orElse(Collections.emptyList())
                .stream()
                .findAny()
                .map(UserEntity::getUid)
                .orElse(0L);
        log.info("delete user {}", userDao.deleteById(uid));
    }
}