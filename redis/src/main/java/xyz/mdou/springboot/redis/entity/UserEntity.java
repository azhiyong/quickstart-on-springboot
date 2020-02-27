package xyz.mdou.springboot.redis.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class UserEntity {

    private Long uid;

    private String name;

    private Date birthday;

    private Date createDate = new Date();

    private Date updateDate = new Date();

}
