package com.zlt.blog.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlt.blog.entity.User;
import com.zlt.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
 * @Date Create in 2020/8/30 18:32
 * @Description TODO
 **/
@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserService userService;

    //获取用户列表
    @GetMapping("/user")
    public String users(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        String orderBy = "create_time desc";
        PageHelper.startPage(pageNum,10,orderBy);
        List<User> users = userService.findAll();
        PageInfo<User> pageInfo = new PageInfo<>(users);
        model.addAttribute("page",pageInfo);
        return "admin/users";
    }

    //跳转到编辑页面
    @GetMapping("/user/{id}/input")
    public String editUser(Model model,@PathVariable Long id){
        model.addAttribute("user",userService.findUserById(id));
        return "admin/users-input";
    }

    //编辑保存用户
    @PostMapping("/user")
    public String saveUser(User user, RedirectAttributes attributes){
        Long u = userService.findNickname(user.getNickname());
        if (u != null && u != user.getId()){
            attributes.addFlashAttribute("message", "昵称已经存在");
            return "redirect:/admin/user/"+ user.getId() +"/input";
        }
        int a = userService.updateUser(user);
        if (a == 0){
            attributes.addFlashAttribute("message", "修该失败");
        }else {
            attributes.addFlashAttribute("message", "修改成功");
        }
        return "redirect:/admin/user";
    }

    //删除用户
    @GetMapping("/user/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        userService.deleteUser(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/user";
    }
}
