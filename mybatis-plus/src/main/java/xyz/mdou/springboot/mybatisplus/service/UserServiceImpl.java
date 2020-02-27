package xyz.mdou.springboot.mybatisplus.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.mdou.springboot.mybatisplus.entity.UserEntity;
import xyz.mdou.springboot.mybatisplus.mapper.UserMapper;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {
}
