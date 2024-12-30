package com.blog.insight_lane.services.impl;

import com.blog.insight_lane.entities.Category;
import com.blog.insight_lane.entities.Post;
import com.blog.insight_lane.entities.User;
import com.blog.insight_lane.exceptions.ResourceNotFoundException;
import com.blog.insight_lane.payloads.PostDto;
import com.blog.insight_lane.payloads.PostResponse;
import com.blog.insight_lane.repositories.CategoryRepository;
import com.blog.insight_lane.repositories.PostRepository;
import com.blog.insight_lane.repositories.UserRepository;
import com.blog.insight_lane.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", " User Id ", userId));
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", " Category Id ", categoryId));
        if (postDto.getContent().length() > 5000) {
            throw new IllegalArgumentException("Content exceeds the maximum allowed length of 5000 characters.");
        }
        Post post = toPost(postDto);
        post.setImageName("default.png");
        post.setCreationDate(new Date());
        post.setLastUpdationDate(new Date());
        post.setCategory(category);
        post.setUser(user);

        Post savedPost = this.postRepository.save(post);
        System.out.printf("User: %s has created a post successfully to the Category: %s%n",
                user.getName(),
                category.getCategoryName());
        return toPostDto(savedPost);

    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepository
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", " Post Id ", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        post.setLastUpdationDate(new Date());

        Post updatedPost = this.postRepository.save(post);
        return this.toPostDto(updatedPost);
    }

    @Override
    public PostDto getPost(Integer postId) {
        Post post = this.postRepository
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", " Post Id ", postId));
        return this.toPostDto(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageable = PageRequest.of(
                pageNumber,
                pageSize,
                "desc".equalsIgnoreCase(sortOrder) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending()
        );
        Page<Post> postPage = this.postRepository.findAll(pageable);
        List<Post> postList = postPage.getContent();
        List<PostDto> postDtoList = postList.stream().map(post -> toPostDto(post)).toList();

        PostResponse response = setPostResponse(postDtoList, postPage);
        return response;
    }

    @Override
    public PostResponse getAllPostByCategory(Integer categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Category category = this.categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", " Category Id ", categoryId));

        Pageable pageable = PageRequest.of(
                pageNumber,
                pageSize,
                "desc".equalsIgnoreCase(sortOrder) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending()
        );
        Page<Post> postPage = this.postRepository.findByCategory(category, pageable);
        List<PostDto> postDtoList = postPage.stream().map(post -> toPostDto(post)).toList();

        PostResponse response = setPostResponse(postDtoList, postPage);
        return response;
    }

    @Override
    public PostResponse getAllPostByUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        User user = this.userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", " User Id ", userId));

        Pageable pageable = PageRequest.of(
                pageNumber,
                pageSize,
                "desc".equalsIgnoreCase(sortOrder) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending()
        );
        Page<Post> postPage = this.postRepository.findByUser(user, pageable);
        List<PostDto> postDtoList = postPage.stream().map(post -> toPostDto(post)).toList();

        PostResponse response = setPostResponse(postDtoList, postPage);
        return response;
    }

    @Override
    public PostResponse searchPosts(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Pageable pageable = PageRequest.of(
                pageNumber,
                pageSize,
                "desc".equalsIgnoreCase(sortOrder) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending()
        );

        Page<Post> postPage = this.postRepository.findByTitleContaining(keyword, pageable);
        List<PostDto> postDtoList = postPage.stream().map(post -> toPostDto(post)).toList();

        PostResponse response = setPostResponse(postDtoList, postPage);
        return response;
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepository
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", " Post Id ", postId));
        this.postRepository.delete(post);
    }

    private Post toPost(PostDto postDto) {
        return this.modelMapper.map(postDto, Post.class);
    }

    private PostDto toPostDto(Post post) {
        return this.modelMapper.map(post, PostDto.class);
    }

    private PostResponse setPostResponse(List<PostDto> postDtoList, Page<Post> postPage) {
        PostResponse response = new PostResponse();
        response.setPosts(postDtoList);
        response.setPageNumber(postPage.getNumber());
        response.setPageSize(postPage.getSize());
        response.setTotalElements(postPage.getTotalElements());
        response.setTotalPages(postPage.getTotalPages());
        response.setHasNext(postPage.hasNext());

        return response;
    }
}
