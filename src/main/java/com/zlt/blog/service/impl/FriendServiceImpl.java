package com.zlt.blog.service.impl;

import com.zlt.blog.dao.FriendMapper;
import com.zlt.blog.entity.Friend;
import com.zlt.blog.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * █████▒█      ██  ▄████▄   ██ ▄█▀       ██████╗ ██╗   ██╗ ██████╗
 * ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒        ██╔══██╗██║   ██║██╔════╝
 * ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░        ██████╔╝██║   ██║██║  ███╗
 * ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄        ██╔══██╗██║   ██║██║   ██║
 * ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄       ██████╔╝╚██████╔╝╚██████╔╝
 * ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒       ╚═════╝  ╚═════╝  ╚═════╝
 * ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░
 * ░ ░    ░░░ ░ ░ ░        ░ ░░ ░
 * ░     ░ ░      ░  ░
 *
 * @Author: 郑龙涛
 * @Date Create in 2020/7/23 16:25
 * @Description TODO
 **/
@Controller
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendMapper friendMapper;

    @Override
    public List<Friend> listFriend() {
        return friendMapper.listFriend();
    }

    @Override
    public int saveFriend(Friend friend) {
        return friendMapper.saveFriend(friend);
    }

    @Override
    public Friend getFriend(Long id) {
        return friendMapper.getFriend(id);
    }

    @Override
    public Friend getFriendByBlogAddress(String blogAddress) {
        return friendMapper.getFriendByBlogAddress(blogAddress);
    }

    @Override
    public int updateFriend(Friend friend) {
        return friendMapper.updateFriend(friend);
    }

    @Override
    public void deleteFriend(Long id) {
        friendMapper.deleteFriend(id);
    }
}
