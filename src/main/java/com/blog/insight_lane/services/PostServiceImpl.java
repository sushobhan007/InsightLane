package com.blog.insight_lane.services;

import com.blog.insight_lane.entities.Category;
import com.blog.insight_lane.entities.Post;
import com.blog.insight_lane.entities.User;
import com.blog.insight_lane.exceptions.ResourceNotFoundException;
import com.blog.insight_lane.payloads.PostDto;
import com.blog.insight_lane.repositories.CategoryRepository;
import com.blog.insight_lane.repositories.PostRepository;
import com.blog.insight_lane.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
                .orElseThrow(() -> new ResourceNotFoundException("User ", " User Id ", userId));
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category ", " Category Id ", categoryId));
        Post post = toPost(postDto);
        post.setImageName("default.png");
        post.setCreationDate(new Date());
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
        return null;
    }

    @Override
    public PostDto getPost(Integer postId) {
        return null;
    }

    @Override
    public List<PostDto> getAllPost() {
        return List.of();
    }

    @Override
    public List<PostDto> getAllPostByCategory(Integer categoryId) {
        return List.of();
    }

    @Override
    public List<PostDto> getAllPostByUser(Integer userId) {
        return List.of();
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        return List.of();
    }

    @Override
    public void deletePost(Integer postId) {

    }

    private Post toPost(PostDto postDto) {
        return this.modelMapper.map(postDto, Post.class);
    }

    private PostDto toPostDto(Post post) {
        return this.modelMapper.map(post, PostDto.class);
    }
}
