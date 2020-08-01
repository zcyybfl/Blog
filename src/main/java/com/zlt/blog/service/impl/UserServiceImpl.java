package com.zlt.blog.service.impl;

import com.zlt.blog.dao.UserMapper;
import com.zlt.blog.entity.User;
import com.zlt.blog.service.UserService;
import com.zlt.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;

    @Override
    public User checkUser(String username, String password) {

        User user = userMapper.findByUsernameAndPassword(username,MD5Utils.code(password));
        return user;
    }
}
