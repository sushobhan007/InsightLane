package com.blog.insight_lane.controllers;

import com.blog.insight_lane.payloads.PostDto;
import com.blog.insight_lane.payloads.PostResponse;
import com.blog.insight_lane.response.ApiResponse;
import com.blog.insight_lane.services.FileService;
import com.blog.insight_lane.services.PostService;
import com.blog.insight_lane.utils.PostUtility;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
                                              @PathVariable Integer userId,
                                              @PathVariable Integer categoryId) {
        PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
        PostDto postDto = this.postService.getPost(postId);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber",
            defaultValue = PostUtility.DEFAULT_PAGE_NUMBER,
            required = false) Integer pageNumber,
                                                    @RequestParam(value = "pageSize",
                                                            defaultValue = PostUtility.DEFAULT_PAGE_SIZE,
                                                            required = false) Integer pageSize,
                                                    @RequestParam(value = "sortBy",
                                                            defaultValue = PostUtility.DEFAULT_SORT_BY,
                                                            required = false) String sortBy,
                                                    @RequestParam(value = "sortOrder",
                                                            defaultValue = PostUtility.DEFAULT_SORT_ORDER,
                                                            required = false) String sortOrder) {
        PostResponse response = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getPostByCategory(@PathVariable Integer categoryId,
                                                          @RequestParam(value = "pageNumber",
                                                                  defaultValue = PostUtility.DEFAULT_PAGE_NUMBER,
                                                                  required = false) Integer pageNumber,
                                                          @RequestParam(value = "pageSize",
                                                                  defaultValue = PostUtility.DEFAULT_PAGE_SIZE,
                                                                  required = false) Integer pageSize,
                                                          @RequestParam(value = "sortBy",
                                                                  defaultValue = PostUtility.DEFAULT_SORT_BY,
                                                                  required = false) String sortBy,
                                                          @RequestParam(value = "sortOrder",
                                                                  defaultValue = PostUtility.DEFAULT_SORT_ORDER,
                                                                  required = false) String sortOrder) {
        PostResponse response = this.postService.
                getAllPostByCategory(categoryId, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getPostByUser(@PathVariable Integer userId,
                                                      @RequestParam(value = "pageNumber",
                                                              defaultValue = PostUtility.DEFAULT_PAGE_NUMBER,
                                                              required = false) Integer pageNumber,
                                                      @RequestParam(value = "pageSize",
                                                              defaultValue = PostUtility.DEFAULT_PAGE_SIZE,
                                                              required = false) Integer pageSize,
                                                      @RequestParam(value = "sortBy",
                                                              defaultValue = PostUtility.DEFAULT_SORT_BY,
                                                              required = false) String sortBy,
                                                      @RequestParam(value = "sortOrder",
                                                              defaultValue = PostUtility.DEFAULT_SORT_ORDER,
                                                              required = false) String sortOrder) {
        PostResponse response = this.postService.getAllPostByUser(userId, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePostById(@RequestBody PostDto postDto, @PathVariable Integer postId) {
        PostDto updatedPostDto = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<>(updatedPostDto, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
        this.postService.deletePost(postId);
        return new ResponseEntity<>(
                new ApiResponse("The post is deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/posts/search")
    public ResponseEntity<PostResponse> searchPostByTitle(@RequestParam(value = "keyword") String keyword,
                                                          @RequestParam(value = "pageNumber",
                                                                  defaultValue = PostUtility.DEFAULT_PAGE_NUMBER,
                                                                  required = false) Integer pageNumber,
                                                          @RequestParam(value = "pageSize",
                                                                  defaultValue = PostUtility.DEFAULT_PAGE_SIZE,
                                                                  required = false) Integer pageSize,
                                                          @RequestParam(value = "sortBy",
                                                                  defaultValue = PostUtility.DEFAULT_SORT_BY,
                                                                  required = false) String sortBy,
                                                          @RequestParam(value = "sortOrder",
                                                                  defaultValue = PostUtility.DEFAULT_SORT_ORDER,
                                                                  required = false) String sortOrder) {
        PostResponse response = this.postService.searchPosts(keyword, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile file,
                                                   @PathVariable Integer postId) throws Exception {
        PostDto postDto = this.postService.getPost(postId);
        String uploadedFileName = this.fileService.uploadImage(path, file);
        postDto.setImageName(uploadedFileName);
        PostDto updatedPost = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @GetMapping(value = "/post/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@RequestParam(name = "imageName") String imageName,
                              HttpServletResponse response) throws Exception {
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}
