package xyz.mdou.springboot.jdbctemplate.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import xyz.mdou.springboot.jdbctemplate.annotation.Column;
import xyz.mdou.springboot.jdbctemplate.annotation.Id;
import xyz.mdou.springboot.jdbctemplate.annotation.Table;

import java.util.Date;

@Data
@Accessors(chain = true)
@Table("user")
public class UserEntity {

    @Id
    private Long uid;

    private String name;

    private Date birthday;

    @Column("create_date")
    private Date createDate;

    @Column("update_date")
    private Date updateDate;
}
