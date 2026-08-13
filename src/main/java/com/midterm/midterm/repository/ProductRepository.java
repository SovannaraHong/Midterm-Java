package com.midterm.midterm.repository;

import com.midterm.midterm.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByExpiredDateBefore(LocalDate date);

    List<Product> findByCategory_CatId(Long catId);

    Optional<Product> findTopByOrderBySoldQtyDesc();

    Optional<Product> findTopByCategory_CatIdOrderBySoldQtyDesc(Long catId);

    List<Product> findTop5ByOrderBySoldQtyDesc();
}