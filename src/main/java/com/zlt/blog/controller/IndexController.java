package com.zlt.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlt.blog.entity.User;
import com.zlt.blog.service.BlogService;
import com.zlt.blog.service.TagService;
import com.zlt.blog.service.TypeService;
import com.zlt.blog.service.UserService;
import com.zlt.blog.util.MD5Utils;
import com.zlt.blog.vo.BlogDetailsVo;
import com.zlt.blog.vo.IndexBlogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

    //初始化首页
    @GetMapping("/")
    public String index(Model model,@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum){
        PageHelper.startPage(pageNum,6);
        List<IndexBlogVo> list = blogService.getIndexBlog();
        PageInfo<IndexBlogVo> pageInfo = new PageInfo<>(list);
        model.addAttribute("page",pageInfo);
        model.addAttribute("types",typeService.getIndexType());
        model.addAttribute("tags",tagService.getIndexTag());
        model.addAttribute("recommendBlogs",blogService.getRecommendBlog());
        return "index";
    }

    @PostMapping("/search")
    public String search(@RequestParam String query, Model model,@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        PageHelper.startPage(pageNum,2);
        List<IndexBlogVo> list = blogService.getSearchBlog(query);
        PageInfo<IndexBlogVo> pageInfo = new PageInfo<>(list);
        model.addAttribute("page",pageInfo);
        model.addAttribute("query",query);
        return "search";
    }

    //进入博客详情
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model){
        BlogDetailsVo blogDetailsVo = blogService.getBlogDetails(id);
        blogDetailsVo.init();
        blogDetailsVo.setTags(tagService.listTag(blogDetailsVo.getTagIds()));
        model.addAttribute("blog",blogDetailsVo);
        return "blog";
    }

    @GetMapping("/footer/newblog")
    public String newblogs(Model model){
        model.addAttribute("newblogs",blogService.getRecommendBlog());
        return "_fragments :: newblogList";
    }

    //跳转到登录
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    //跳转到注册
    @GetMapping("/register")
    public String registerPage(){
        return "register";
    }

    //登录
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user = userService.checkUser(username,password);
        if (user != null){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "redirect:/";
        }else {
            attributes.addFlashAttribute("message","用户名或密码错误");
            return "redirect:/login";
        }
    }

    //注册
    @PostMapping("/register")
    public String register(User user,@RequestParam String password1,RedirectAttributes attributes){
        System.out.println(user.getNickname());
        System.out.println(user.getUsername());
        System.out.println(user.getEmail());
        System.out.println(password1);

        if (userService.findUsername(user.getUsername())!=null){
            attributes.addFlashAttribute("message","用户名重复");
            return "redirect:/register";
        }
        if (userService.findNickname(user.getNickname())!=null){
            attributes.addFlashAttribute("message","昵称重复");
            return "redirect:/register";
        }
        if (!(password1.equals(user.getPassword()))){
            attributes.addFlashAttribute("message","两次密码输入不一致");
            return "redirect:/register";
        }
        user.setPassword(MD5Utils.code(user.getPassword()));
        System.out.println(user.getPassword());
        userService.saveUser(user);
        return "redirect:/login";
    }

    //注销
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/";
    }
}
