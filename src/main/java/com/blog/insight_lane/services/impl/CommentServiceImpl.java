package com.blog.insight_lane.services.impl;

import com.blog.insight_lane.entities.Comment;
import com.blog.insight_lane.entities.Post;
import com.blog.insight_lane.exceptions.ResourceNotFoundException;
import com.blog.insight_lane.payloads.CommentDto;
import com.blog.insight_lane.repositories.CommentRepository;
import com.blog.insight_lane.repositories.PostRepository;
import com.blog.insight_lane.services.CommentService;
import com.blog.insight_lane.utils.ModelMapperUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//TODO: Set User for comments
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapperUtility modelMapperUtility;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Post", " Post Id ", postId)
                );
        Comment comment = modelMapperUtility.toComment(commentDto);
        comment.setPost(post);
        Comment savedComment = this.commentRepository.save(comment);
        System.out.println("Post: -> " + post.getTitle());
        System.out.println("New Comment Added: -> " + comment.getContent());
        return modelMapperUtility.toCommentDto(savedComment);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", " Comment Id ", commentId)
        );
        commentRepository.delete(comment);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, Integer commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", " Comment Id ", commentId)
        );
        comment.setContent(commentDto.getContent());
        Comment savedComment = this.commentRepository.save(comment);
        System.out.println("Updated Comment: -> " + comment.getContent());
        return modelMapperUtility.toCommentDto(savedComment);
    }
}
