package xyz.mdou.springboot.mybatismapper.mapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.mdou.springboot.mybatismapper.MybatisMapperApplicationTest;
import xyz.mdou.springboot.mybatismapper.entity.UserEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class UserMapperTestMapper extends MybatisMapperApplicationTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSave() {
        List<UserEntity> userEntities = new ArrayList<>();
        UserEntity user1 = new UserEntity().setName("user1").setBirthday(new Date());
        userMapper.insert(user1);
        UserEntity user2 = new UserEntity().setName("user2").setBirthday(new Date());
        UserEntity user3 = new UserEntity().setName("user3").setBirthday(new Date());
        userEntities.add(user2);
        userEntities.add(user3);
        userMapper.insertList(userEntities);

        log.info("all user: {}", userMapper.selectAll());
    }

    @Test
    public void testGetOne() {
        UserEntity user = userMapper.selectByPrimaryKey(1L);
        log.info("getOne user: {}", user);
    }

    @Test
    public void testQueryByPageAndSort() {
        testSave();
        int currentPage = 1;
        int pageSize = 5;
        String orderBy = "uid desc";
        int count = userMapper.selectCount(null);
        PageHelper.startPage(currentPage, pageSize, orderBy);

        List<UserEntity> users = userMapper.selectAll();
        PageInfo<UserEntity> userPageInfo = new PageInfo<>(users);
        Assert.assertEquals(5, userPageInfo.getSize());
        Assert.assertEquals(count, userPageInfo.getTotal());
        log.debug("【userPageInfo】= {}", userPageInfo);
    }
}