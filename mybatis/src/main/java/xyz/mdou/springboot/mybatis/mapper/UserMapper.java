package xyz.mdou.springboot.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import xyz.mdou.springboot.mybatis.entity.UserEntity;

import java.util.List;

@Component
@Mapper
public interface UserMapper {

    @Select("select * from user")
    List<UserEntity> findAll();

    @Select("select * from user where uid=#{uid}")
    UserEntity getOne(@Param("uid") Long uid);

    int save(@Param("user") UserEntity user);

    void saveAll(@Param("users") List<UserEntity> users);

    int deleteById(@Param("uid") Long uid);
}
