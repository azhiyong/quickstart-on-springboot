package xyz.mdou.springboot.jdbctemplate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import xyz.mdou.springboot.jdbctemplate.entity.UserEntity;

@Repository
public class UserDao extends BaseDao<UserEntity, Long> {

    @Autowired
    public UserDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }
}
