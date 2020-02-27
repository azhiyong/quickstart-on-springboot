package xyz.mdou.springboot.mybatisplus.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.mdou.springboot.mybatisplus.RedisApplicationTest;
import xyz.mdou.springboot.redis.mapper.UserMapper;

@Slf4j
public class UserMapperTestPlus extends RedisApplicationTest {

    @Autowired
    private UserMapper userMapper;

}