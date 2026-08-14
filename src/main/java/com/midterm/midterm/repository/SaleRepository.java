package com.midterm.midterm.repository;

import com.midterm.midterm.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findByStaff_Sid(Long staffId);
}