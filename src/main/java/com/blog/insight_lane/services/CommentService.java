package com.blog.insight_lane.services;

import com.blog.insight_lane.payloads.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Integer postId);

    void deleteComment(Integer commentId);

    CommentDto updateComment(CommentDto commentDto, Integer commentId);
}
