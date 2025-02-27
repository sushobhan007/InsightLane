package com.blog.insight_lane.services;

import com.blog.insight_lane.payloads.PostDto;
import com.blog.insight_lane.payloads.PostResponse;

public interface PostService {
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    PostDto updatePost(PostDto postDto, Integer postId);

    PostDto getPost(Integer postId);

    PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    PostResponse getAllPostByCategory(Integer categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    PostResponse getAllPostByUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    PostResponse searchPosts(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    void deletePost(Integer postId);

}
