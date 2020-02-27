package xyz.mdou.springboot.mybatismapper.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "user")
@Accessors(chain = true)
public class UserEntity {

    private Long uid;

    private String name;

    private Date birthday;

    private Date createDate;

    private Date updateDate;

}
