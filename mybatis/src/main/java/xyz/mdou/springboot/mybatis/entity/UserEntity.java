package xyz.mdou.springboot.mybatis.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class UserEntity {

    private Long uid;

    private String name;

    private Date birthday;

    private Date createDate;

    private Date updateDate;

}
