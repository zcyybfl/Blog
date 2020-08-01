package com.zlt.blog.dao;

import com.zlt.blog.entity.Blog;
import com.zlt.blog.vo.BlogDetailsVo;
import com.zlt.blog.vo.BlogQuery;
import com.zlt.blog.vo.IndexBlogVo;
import com.zlt.blog.vo.SearchBlog;
import org.apache.ibatis.annotations.Param;
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
 * @Date Create in 2020/7/17 14:36
 * @Description 博客持久层接口
 **/
@Repository
public interface BlogMapper {

    //新增保存博客
    int saveBlog(Blog blog);

    //保存博客的标签
    int saveBlogTags(Blog blog);

    //获取管理博客列表
    List<BlogQuery> getAllBlogQuery();

    //根据id获得博客
    Blog getBlogById(Long id);

    //博客详情的获取
    BlogDetailsVo getBlogDetails(Long id);

    //通过分类查询得到博客列表,展示到分类页
    List<IndexBlogVo> getBlogByTypeId(Long id);

    //通过标签查询得到博客列表,展示到标签页
    List<IndexBlogVo> getBlogByTagId(Long id);

    //首页得到博客内容
    List<IndexBlogVo> getIndexBlog();

    //搜索文章
    List<IndexBlogVo> getSearchBlog(@Param(value = "query") String query);

    //首页最新推荐文章获取
    List<Blog> getRecommendBlog();

    //根据标题、分类、推荐查询博客
    List<BlogQuery> searchBlogByTitleOrTypeOrRecommend(SearchBlog searchBlog);

    //编辑修改博客
    int updateBlog(Blog blog);

    //浏览次数的增加
    int updateViews(Long id);

    //删除博客
    void deleteBlog(Long id);

    //根据博客Id删除博客标签
    void deleteBlogTags(Long id);

}
