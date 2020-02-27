package xyz.mdou.springboot.mybatisplus.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;
import xyz.mdou.springboot.mybatisplus.entity.UserEntity;

@Component
public interface UserMapper extends BaseMapper<UserEntity> {


}
