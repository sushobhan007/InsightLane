package com.blog.insight_lane.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private Integer postId;
    private String title;
    private String content;
    private String imageName;
    private Date creationDate;
    private Date lastUpdationDate;
    private CategoryDto category;
    private UserDto user;
}
