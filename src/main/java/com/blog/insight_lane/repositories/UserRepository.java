package com.blog.insight_lane.repositories;

import com.blog.insight_lane.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
