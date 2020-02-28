package xyz.mdou.springboot.ehcache.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import xyz.mdou.springboot.ehcache.entity.UserEntity;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class UserService {

    private static final Map<Long, UserEntity> userMap = new ConcurrentHashMap<>();

    static {
        userMap.put(1L, new UserEntity().setUid(1L).setName("young").setBirthday(Date.from(Instant.from(LocalDate.of(2010, 3, 15).atStartOfDay(ZoneId.systemDefault())))));
        userMap.put(2L, new UserEntity().setUid(2L).setName("user2").setBirthday(Date.from(Instant.from(LocalDate.of(1990, 1, 4).atStartOfDay(ZoneId.systemDefault())))));
        userMap.put(3L, new UserEntity().setUid(3L).setName("user3").setBirthday(Date.from(Instant.from(LocalDate.of(2008, 12, 22).atStartOfDay(ZoneId.systemDefault())))));
    }

    @CachePut(key = "#user.uid", value = "user")
    public UserEntity saveOrUpdate(UserEntity user) {
        userMap.put(user.getUid(), user);
        log.info("save user {}", user);
        return user;
    }

    @Cacheable(key = "#uid", value = "user")
    public UserEntity getByUid(Long uid) {
        log.info("get user {}", uid);
        return userMap.get(uid);
    }

    @CacheEvict(value = "user", key = "#uid")
    public void deleteByUid(Long uid) {
        userMap.remove(uid);
        log.info("delete user {}", uid);
    }

}
