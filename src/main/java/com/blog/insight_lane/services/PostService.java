package com.blog.insight_lane.services;

import com.blog.insight_lane.payloads.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    PostDto updatePost(PostDto postDto, Integer postId);

    PostDto getPost(Integer postId);

    List<PostDto> getAllPost();

    List<PostDto> getAllPostByCategory(Integer categoryId);

    List<PostDto> getAllPostByUser(Integer userId);

    List<PostDto> searchPosts(String keyword);

    void deletePost(Integer postId);

}
