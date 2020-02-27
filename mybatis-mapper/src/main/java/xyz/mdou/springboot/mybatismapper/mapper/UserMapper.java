package xyz.mdou.springboot.mybatismapper.mapper;


import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import xyz.mdou.springboot.mybatismapper.entity.UserEntity;

@Component
public interface UserMapper extends Mapper<UserEntity>, MySqlMapper<UserEntity> {


}
