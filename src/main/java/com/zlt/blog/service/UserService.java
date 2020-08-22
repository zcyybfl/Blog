package com.zlt.blog.service;

import com.zlt.blog.entity.User;

public interface UserService {

    User checkUser(String username, String password);

    int saveUser(User user);

    Long findUsername(String username);

    Long findNickname(String nickname);
}
