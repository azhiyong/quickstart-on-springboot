package xyz.mdou.springboot.jdbctemplate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.mdou.springboot.jdbctemplate.dao.UserDao;
import xyz.mdou.springboot.jdbctemplate.entity.UserEntity;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public boolean insert(UserEntity entity) {
        return userDao.insert(entity) > 0;
    }

    public UserEntity getByUid(Long uid) {
        return userDao.getByPrimaryId(uid);
    }

    public boolean updateByUid(Long uid, UserEntity entity) {
        return userDao.updateByPrimaryId(uid, entity) > 0;
    }

    public boolean deleteByUid(Long uid) {
        return userDao.deleteByPrimaryId(uid) > 0;
    }
}
