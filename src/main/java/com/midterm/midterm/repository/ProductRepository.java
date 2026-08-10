package com.midterm.midterm.repository;

import com.midterm.midterm.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByExpiredDateBefore(LocalDate date);

    List<Product> findByCategory_CatId(Long catId);
}