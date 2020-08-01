package com.zlt.blog.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlt.blog.entity.Friend;
import com.zlt.blog.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Date;
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
 * @Date Create in 2020/7/23 16:17
 * @Description 友链管理
 **/
@Controller
@RequestMapping("/admin")
public class FriendController {

    @Autowired
    private FriendService friendService;

    //    查询所有友链
    @GetMapping("/friends")
    public String friend(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        PageHelper.startPage(pageNum,10);
        List<Friend> list = friendService.listFriend();
        PageInfo<Friend> pageInfo = new PageInfo<>(list);
        model.addAttribute("page",pageInfo);
        return "admin/friends";
    }

    //    跳转友链新增页面
    @GetMapping("/friends/input")
    public String input(Model model) {
        model.addAttribute("friend", new Friend());
        return "admin/friends-input";
    }

    //    友链新增
    @PostMapping("/friends")
    public String post(@Valid Friend friend, BindingResult result, RedirectAttributes attributes){

        Friend friend1 = friendService.getFriendByBlogAddress(friend.getBlogAddress());
        if (friend1 != null) {
            attributes.addFlashAttribute("message", "不能添加相同的网址");
            return "redirect:/admin/friends/input";
        }

        friend.setCreateTime(new Date());
        int f = friendService.saveFriend(friend);
        if (f == 0 ) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/friends";
    }

    //    跳转友链修改页面
    @GetMapping("/friends/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("friend", friendService.getFriend(id));
        return "admin/friends-input";
    }

    //    编辑修改友链
    @PostMapping("/friends/{id}")
    public String editPost(@Valid Friend friend, RedirectAttributes attributes) {
        int t = friendService.updateFriend(friend);
        if (t == 0 ) {
            attributes.addFlashAttribute("message", "编辑失败");
        } else {
            attributes.addFlashAttribute("message", "编辑成功");
        }
        return "redirect:/admin/friends";
    }

    //    删除友链
    @GetMapping("/friends/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        friendService.deleteFriend(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/friends";
    }

}
