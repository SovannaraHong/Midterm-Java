package com.midterm.midterm.repository;

import com.midterm.midterm.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}