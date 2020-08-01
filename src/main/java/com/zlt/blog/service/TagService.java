package com.zlt.blog.service;

import com.zlt.blog.entity.Tag;
import com.zlt.blog.entity.Type;

import java.util.List;


public interface TagService {

    //新增标签
    int saveTag(Tag tag);

    //根据id查询标签
    Tag getTag(Long id);

    //根据名称查询标签
    Tag getTagByName(String name);

    //得到所有标签
    List<Tag> getAllTag();

    //博客新增时查询博客的标签
    List<Tag> listTag(String ids);


    //首页标签获取
    List<Tag> getIndexTag();

    //更新标签
    int updateTag(Tag tag);

    //删除标签
    void deleteTag(Long id);

}
