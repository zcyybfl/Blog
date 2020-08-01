package com.zlt.blog.service;

import com.zlt.blog.entity.Type;


import java.util.List;

public interface TypeService {

    //新增分类
    int saveType(Type type);

    //根据id查询分类
    Type getType(Long id);

    //根据分类名称查询分类
    Type getTypeByName(String name);

    //查询所有分类
    List<Type> getAllType();

    //首页分类获取
    List<Type> getIndexType();

    //编辑修改分类
    int updateType(Type type);

    //删除分类
    void deleteType(Long id);


}
