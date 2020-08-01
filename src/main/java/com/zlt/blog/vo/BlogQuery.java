package com.zlt.blog.vo;

import com.zlt.blog.entity.Type;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 博客管理列表实体类
 */

@Data
@NoArgsConstructor
public class BlogQuery {

    private Long id;
    private String title;
    private Date updateTime;
    private Boolean recommend;
    private Boolean published;
    private Long typeId;
    private Type type;
}
