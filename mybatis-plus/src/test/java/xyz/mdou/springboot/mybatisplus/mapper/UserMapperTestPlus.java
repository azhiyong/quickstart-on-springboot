package xyz.mdou.springboot.mybatisplus.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.mdou.springboot.mybatisplus.MybatisPlusApplicationTest;

@Slf4j
public class UserMapperTestPlus extends MybatisPlusApplicationTest {

    @Autowired
    private UserMapper userMapper;

}