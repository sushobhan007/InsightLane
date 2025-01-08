package com.blog.insight_lane.utils;

import com.blog.insight_lane.entities.Category;
import com.blog.insight_lane.entities.Comment;
import com.blog.insight_lane.entities.Post;
import com.blog.insight_lane.entities.User;
import com.blog.insight_lane.payloads.CategoryDto;
import com.blog.insight_lane.payloads.CommentDto;
import com.blog.insight_lane.payloads.PostDto;
import com.blog.insight_lane.payloads.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperUtility {
    @Autowired
    public ModelMapper modelMapper;

    public Comment toComment(CommentDto commentDto) {
        return modelMapper.map(commentDto, Comment.class);
    }

    public CommentDto toCommentDto(Comment comment) {
        return modelMapper.map(comment, CommentDto.class);
    }

    public Category toCategory(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }

    public CategoryDto toCategoryDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

    public Post toPost(PostDto postDto) {
        return modelMapper.map(postDto, Post.class);
    }

    public PostDto toPostDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }

    public User toUser(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    public UserDto toUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
