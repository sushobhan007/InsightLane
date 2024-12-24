package com.blog.insight_lane.controllers;

import com.blog.insight_lane.payloads.PostDto;
import com.blog.insight_lane.payloads.PostResponse;
import com.blog.insight_lane.response.ApiResponse;
import com.blog.insight_lane.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
                                       @PathVariable Integer userId,
                                       @PathVariable Integer categoryId) {
        PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}")
    ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
        PostDto postDto = this.postService.getPost(postId);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @GetMapping("/posts")
    ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                             @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                             @RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
                                             @RequestParam(value = "sortOrder", defaultValue = "asc", required = false) String sortOrder) {
        PostResponse response = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    ResponseEntity<PostResponse> getPostByCategory(@PathVariable Integer categoryId,
                                                   @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                   @RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
                                                   @RequestParam(value = "sortOrder", defaultValue = "asc", required = false) String sortOrder) {
        PostResponse response = this.postService.getAllPostByCategory(categoryId, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/posts")
    ResponseEntity<PostResponse> getPostByUser(@PathVariable Integer userId,
                                               @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                               @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                               @RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
                                               @RequestParam(value = "sortOrder", defaultValue = "asc", required = false) String sortOrder) {
        PostResponse response = this.postService.getAllPostByUser(userId, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}")
    ResponseEntity<PostDto> updatePostById(@RequestBody PostDto postDto, @PathVariable Integer postId) {
        PostDto updatedPostDto = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<>(updatedPostDto, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
        this.postService.deletePost(postId);
        return new ResponseEntity<>(
                new ApiResponse("The post is deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/posts/search")
    ResponseEntity<PostResponse> searchPostByTitle(@RequestParam(value = "keyword") String keyword,
                                                   @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                   @RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
                                                   @RequestParam(value = "sortOrder", defaultValue = "asc", required = false) String sortOrder) {
        PostResponse response = this.postService.searchPosts(keyword, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
