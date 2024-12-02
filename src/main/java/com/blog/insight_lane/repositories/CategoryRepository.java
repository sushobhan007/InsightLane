package com.blog.insight_lane.repositories;

import com.blog.insight_lane.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
