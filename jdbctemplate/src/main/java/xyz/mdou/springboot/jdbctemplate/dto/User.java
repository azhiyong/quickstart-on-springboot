package xyz.mdou.springboot.jdbctemplate.dto;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private String name;
    private Date birthday;
}
