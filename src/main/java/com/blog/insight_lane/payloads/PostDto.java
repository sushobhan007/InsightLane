package com.blog.insight_lane.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private String title;
    private String content;
    private String imageName;
    private Date creationDate;
    private CategoryDto category;
    private UserDto user;
}
