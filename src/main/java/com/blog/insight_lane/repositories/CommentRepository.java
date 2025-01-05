package com.blog.insight_lane.repositories;

import com.blog.insight_lane.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
