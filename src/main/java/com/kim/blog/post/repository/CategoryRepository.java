package com.kim.blog.post.repository;

import com.kim.blog.post.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {


    Optional<Category> findByCategoryName(String categoryName);
}
