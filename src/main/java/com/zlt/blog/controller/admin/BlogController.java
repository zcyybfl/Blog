package com.zlt.blog.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlt.blog.entity.Blog;
import com.zlt.blog.entity.User;
import com.zlt.blog.service.BlogService;
import com.zlt.blog.service.TagService;
import com.zlt.blog.service.TypeService;
import com.zlt.blog.vo.BlogQuery;
import com.zlt.blog.vo.SearchBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    //获取管理博客列表
    @GetMapping("/blogs")
    public String blogs(Model model,@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        //按照排序字段 倒序 排序
        String orderBy = "update_time desc";
        PageHelper.startPage(pageNum,10,orderBy);
        List<BlogQuery> list = blogService.getAllBlogQuery();
        PageInfo<BlogQuery> pageInfo = new PageInfo<>(list);
        model.addAttribute("types",typeService.getAllType());
        model.addAttribute("page",pageInfo);
        return "admin/blogs";
    }

    //博客查询
    @PostMapping("/blogs/search")
    public String search(SearchBlog searchBlog, Model model,@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        List<BlogQuery> blogBySearch = blogService.getBlogBySearch(searchBlog);
        PageHelper.startPage(pageNum, 2);
        PageInfo<BlogQuery> pageInfo = new PageInfo<>(blogBySearch);
        model.addAttribute("page",pageInfo);
        return "admin/blogs :: blogList";
    }

    //跳转到博客新增页面
    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("types",typeService.getAllType());
        model.addAttribute("tags",tagService.getAllTag());
        model.addAttribute("blog",new Blog());
        return "admin/blogs-input";
    }

    //初始化标签和分类
    private void setTypeAndTag(Model model){
        model.addAttribute("types",typeService.getAllType());
        model.addAttribute("tags",tagService.getAllTag());
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return INPUT;
    }

    //处理博客新增
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){
        //新增的时候需要传递blog对象，blog对象需要有user
        blog.setUser((User) session.getAttribute("user"));
        //设置blog的type
        blog.setType(typeService.getType(blog.getType().getId()));
        //设置blog中typeId属性
        blog.setTypeId(blog.getType().getId());
        //设置用户id
        blog.setUserId(blog.getUser().getId());
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));


        int b;
        if (blog.getId() == null){
            b = blogService.saveBlog(blog);
            blogService.saveBlogTags(blog);
            if (b == 0){
                attributes.addFlashAttribute("message", "新增失败");
            }else {
                attributes.addFlashAttribute("message", "新增成功");
            }
        }else {
            b = blogService.updateBlog(blog);
            if (b == 0){
                attributes.addFlashAttribute("message", "修改失败");
            }else {
                attributes.addFlashAttribute("message", "修改成功");
            }
        }

        return REDIRECT_LIST;
    }

    //删除博客
    @GetMapping("/blogs/{id}/delete")
    private String delete(@PathVariable Long id, RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");
        return REDIRECT_LIST;
    }

}
