package com.blog.insight_lane.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private Integer commentId;
    private String content;
}
