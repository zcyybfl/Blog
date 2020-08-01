package com.zlt.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlt.blog.entity.Type;
import com.zlt.blog.service.BlogService;
import com.zlt.blog.service.TypeService;
import com.zlt.blog.vo.BlogQuery;
import com.zlt.blog.vo.IndexBlogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PathVariable Long id, Model model,@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum){
        List<Type> types = typeService.getIndexType();
        if (id == -1){
            id = types.get(0).getId();
        }
        PageHelper.startPage(pageNum,6);
        List<IndexBlogVo> list = blogService.getBlogByTypeId(id);
        PageInfo<IndexBlogVo> pageInfo = new PageInfo<>(list);
        model.addAttribute("types",types);
        model.addAttribute("page",pageInfo);
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}
