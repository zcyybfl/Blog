package com.zlt.blog.service.impl;

import com.zlt.blog.dao.UserMapper;
import com.zlt.blog.entity.User;
import com.zlt.blog.service.UserService;
import com.zlt.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;

    @Override
    public User checkUser(String username, String password) {

        User user = userMapper.findByUsernameAndPassword(username,MD5Utils.code(password));
        return user;
    }

    @Override
    public int saveUser(User user) {
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setType(3);
        user.setAvatar("https://picsum.photos/id/1005/100/100");
        return userMapper.saveUser(user);
    }

    @Override
    public Long findUsername(String username) {
        return userMapper.findUsername(username);
    }

    @Override
    public Long findNickname(String nickname) {
        return userMapper.findNickname(nickname);
    }

    @Override
    public User findUserById(Long id) {
        return userMapper.findUserById(id);
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAllUser();
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateUserNicknameAndType(user);
    }

    @Override
    public void deleteUser(Long id) {
        userMapper.deleteUser(id);
    }
}
