package com.zlt.blog.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlt.blog.entity.Tag;
import com.zlt.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    //查看所有标签
    @GetMapping("/tags")
    public String tags(Model model,@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        //按照排序字段 倒序 排序
        String orderBy = "id desc";
        PageHelper.startPage(pageNum,10,orderBy);
        List<Tag> tags = tagService.getAllTag();
        PageInfo<Tag> page = new PageInfo<>(tags);
        model.addAttribute("page",page);
        return "admin/tags";
    }

    //标签新增
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

    //标签修改
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tags-input";
    }

    //标签新增处理
    @PostMapping("/tags")
    public String post(@Valid Tag tag, RedirectAttributes attributes){
        Tag t = tagService.getTagByName(tag.getName());
        if (t != null){
            attributes.addFlashAttribute("message", "不能添加重复的分类");
            return "redirect:/admin/tags/input";
        }
        int tag1 = tagService.saveTag(tag);
        if (tag1 == 0){
            attributes.addFlashAttribute("message", "新增失败");
        }else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/tags";
    }

    //标签编辑处理
    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag,  @PathVariable Long id, RedirectAttributes attributes){
        Tag t = tagService.getTagByName(tag.getName());
        if (t != null){
            attributes.addFlashAttribute("message", "不能添加重复的分类");
            return "redirect:/admin/tags/input";
        }
        int tag1 = tagService.updateTag(tag);
        if (tag1 == 0){
            attributes.addFlashAttribute("message", "修该失败");
        }else {
            attributes.addFlashAttribute("message", "修改成功");
        }
        return "redirect:/admin/tags";
    }

    //标签删除
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }
}
