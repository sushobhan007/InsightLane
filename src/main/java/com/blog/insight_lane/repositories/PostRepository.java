package com.blog.insight_lane.repositories;

import com.blog.insight_lane.entities.Category;
import com.blog.insight_lane.entities.Post;
import com.blog.insight_lane.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Page<Post> findByUser(User user, Pageable pageable);

    Page<Post> findByCategory(Category category, Pageable pageable);

    Page<Post> findByTitleContaining(String title,Pageable pageable);
}
