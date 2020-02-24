package xyz.mdou.springboot.jdbctemplate.entity;

import lombok.Data;
import xyz.mdou.springboot.jdbctemplate.annotation.Column;
import xyz.mdou.springboot.jdbctemplate.annotation.PrimaryKey;
import xyz.mdou.springboot.jdbctemplate.annotation.Table;

import java.util.Date;

@Data
@Table("user")
public class UserEntity {

    @PrimaryKey
    private Long uid;

    private String name;

    private Date birthday;

    @Column("create_date")
    private Date createDate;

    @Column("update_date")
    private Date updateDate;
}
