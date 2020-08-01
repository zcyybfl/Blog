package com.zlt.blog.dao;

import com.zlt.blog.entity.Tag;
import com.zlt.blog.entity.Type;
import org.springframework.stereotype.Repository;

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
 * @Date Create in 2020/7/17 13:38
 * @Description 标签持久层接口
 **/
@Repository
public interface TagMapper {

    //新增保存标签
    int saveTag(Tag tag);

    //根据Id查询标签
    Tag getTag(Long id);

    //查询所有标签
    List<Tag> getAllTag();

    //根据名称查询标签
    Tag getTagByName(String name);

    //首页标签获取
    List<Tag> getIndexTag();

    //编辑修改标签
    int updateTag(Tag tag);

    //删除标签
    void deleteTag(Long id);

    List<Tag> listTag(List<Long> ids);

}
