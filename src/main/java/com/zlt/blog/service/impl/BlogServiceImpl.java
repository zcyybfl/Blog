package com.zlt.blog.service.impl;

import com.zlt.blog.NotFoundException;
import com.zlt.blog.dao.BlogMapper;
import com.zlt.blog.entity.Blog;
import com.zlt.blog.service.BlogService;
import com.zlt.blog.util.MarkdownUtils;
import com.zlt.blog.vo.BlogDetailsVo;
import com.zlt.blog.vo.BlogQuery;
import com.zlt.blog.vo.IndexBlogVo;
import com.zlt.blog.vo.SearchBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public int saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        blog.setCommentCount(0);
        return blogMapper.saveBlog(blog);
    }

    @Override
    public int saveBlogTags(Blog blog) {
        return blogMapper.saveBlogTags(blog);
    }

    @Override
    public List<BlogQuery> getAllBlogQuery() {
        return blogMapper.getAllBlogQuery();
    }

    @Override
    public Blog getBlog(Long id) {
        return blogMapper.getBlogById(id);
    }

    @Override
    public BlogDetailsVo getBlogDetails(Long id) {
        BlogDetailsVo detailedBlog = blogMapper.getBlogDetails(id);
        if (detailedBlog == null) {
            throw new NotFoundException("该博客不存在");
        }
        String content = detailedBlog.getContent();
        detailedBlog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        blogMapper.updateViews(id);
        return detailedBlog;
    }

    @Override
    public List<IndexBlogVo> getBlogByTypeId(Long id) {
        return blogMapper.getBlogByTypeId(id);
    }

    @Override
    public List<IndexBlogVo> getBlogByTagId(Long id) {
        return blogMapper.getBlogByTagId(id);
    }

    @Override
    public List<IndexBlogVo> getIndexBlog() {
        return blogMapper.getIndexBlog();
    }

    @Override
    public List<IndexBlogVo> getSearchBlog(String query) {
        return blogMapper.getSearchBlog(query);
    }

    @Override
    public List<Blog> getRecommendBlog() {
        return blogMapper.getRecommendBlog();
    }

    @Override
    public List<BlogQuery> getBlogBySearch(SearchBlog searchBlog) {
        return blogMapper.searchBlogByTitleOrTypeOrRecommend(searchBlog);
    }

    @Override
    public int updateBlog(Blog blog) {
        blogMapper.deleteBlogTags(blog.getId());
        blog.setUpdateTime(new Date());
        blogMapper.saveBlogTags(blog);
        return blogMapper.updateBlog(blog);
    }

    @Override
    public void deleteBlog(Long id) {
        blogMapper.deleteBlogTags(id);
        blogMapper.deleteBlog(id);
    }


}
