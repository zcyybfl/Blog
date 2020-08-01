package com.zlt.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlt.blog.entity.Tag;
import com.zlt.blog.service.BlogService;
import com.zlt.blog.service.TagService;
import com.zlt.blog.vo.IndexBlogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{id}")
    public String Tags(@PathVariable Long id, Model model,@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        List<Tag> tags = tagService.getIndexTag();
        if (id == -1){
            id = tags.get(0).getId();
        }
        PageHelper.startPage(pageNum,6);
        List<IndexBlogVo> list = blogService.getBlogByTagId(id);
        PageInfo<IndexBlogVo> pageInfo = new PageInfo<>(list);
        model.addAttribute("tags",tags);
        model.addAttribute("page",pageInfo);
        model.addAttribute("activeTagId",id);
        return "tags";
    }
}
