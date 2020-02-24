package xyz.mdou.springboot.jdbctemplate.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.mdou.springboot.jdbctemplate.dto.User;
import xyz.mdou.springboot.jdbctemplate.entity.UserEntity;
import xyz.mdou.springboot.jdbctemplate.service.UserService;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {
    private static final Gson GSON = new Gson();

    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseBody
    public String addUser(@RequestBody User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(user.getName());
        userEntity.setBirthday(user.getBirthday());
        userEntity.setCreateDate(new Date());
        userEntity.setUpdateDate(new Date());
        userService.insert(userEntity);
        return "success";
    }

    @GetMapping
    @ResponseBody
    public String getUser(@RequestParam Long uid) {
        return Optional.ofNullable(userService.getByUid(uid))
                .map(GSON::toJson)
                .orElse("null");
    }
}
