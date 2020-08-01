package com.zlt.blog.service;


import com.zlt.blog.entity.Blog;
import com.zlt.blog.vo.BlogDetailsVo;
import com.zlt.blog.vo.BlogQuery;
import com.zlt.blog.vo.IndexBlogVo;
import com.zlt.blog.vo.SearchBlog;

import java.util.List;

public interface BlogService {

    //新增保存博客
    int saveBlog(Blog blog);

    //保存博客标签
    int saveBlogTags(Blog blog);

    //获取博客管理的列表
    List<BlogQuery> getAllBlogQuery();

    //根据id获得博客
    Blog getBlog(Long id);

    //博客详情的获取
    BlogDetailsVo getBlogDetails(Long id);

    //通过分类id查询得到博客列表,展示到分类页
    List<IndexBlogVo> getBlogByTypeId(Long id);

    //通过标签id查询得到博客列表,展示到标签页
    List<IndexBlogVo> getBlogByTagId(Long id);

    //首页得到博客内容
    List<IndexBlogVo> getIndexBlog();

    //搜索文章
    List<IndexBlogVo> getSearchBlog(String query);

    //首页最新推荐文章获取
    List<Blog> getRecommendBlog();

    //根据标题、分类、推荐查询博客
    List<BlogQuery> getBlogBySearch(SearchBlog searchBlog);

    //编辑修改博客
    int updateBlog(Blog blog);

    //删除博客
    void deleteBlog(Long id);
}
