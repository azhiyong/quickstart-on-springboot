package xyz.mdou.springboot.jpa.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.mdou.springboot.jpa.JpaApplicationTest;
import xyz.mdou.springboot.jpa.entity.UserEntity;

import java.util.Date;
import java.util.List;

@Slf4j
public class UserRepositoryTest extends JpaApplicationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSave() {
        List<UserEntity> userEntities = userRepository.findAll();
        if (userEntities.size() == 0) {
            UserEntity user1 = new UserEntity().setName("user1").setBirthday(new Date());
            UserEntity user2 = new UserEntity().setName("user2").setBirthday(new Date());
            UserEntity user3 = new UserEntity().setName("user3").setBirthday(new Date());
            userEntities.add(user1);
            userEntities.add(user2);
            userEntities.add(user3);
            userRepository.saveAll(userEntities);
        }
        log.info("all user: {}", userRepository.findAll());
    }
}