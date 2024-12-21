package com.blog.insight_lane.repositories;

import com.blog.insight_lane.entities.Category;
import com.blog.insight_lane.entities.Post;
import com.blog.insight_lane.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUser(User user);

    List<Category> findByCategory(Category category);
}
