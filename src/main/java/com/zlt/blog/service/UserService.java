package com.zlt.blog.service;

import com.zlt.blog.entity.User;

import java.util.List;

public interface UserService {

    User checkUser(String username, String password);

    int saveUser(User user);

    Long findUsername(String username);

    Long findNickname(String nickname);

    User findUserById(Long id);

    List<User> findAll();

   int updateUser(User user);

    void deleteUser(Long id);
}
