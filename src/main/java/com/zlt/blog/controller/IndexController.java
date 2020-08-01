package com.zlt.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlt.blog.service.BlogService;
import com.zlt.blog.service.TagService;
import com.zlt.blog.service.TypeService;
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

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

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
}
