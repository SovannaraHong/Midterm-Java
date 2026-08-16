package com.midterm.midterm.repository;

import com.midterm.midterm.entities.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SaleDetailRepository extends JpaRepository<SaleDetail, Long> {

    Optional<SaleDetail> findBySaleDetailIdAndIsDeletedFalse(Long saleDetailId);

    List<SaleDetail> findAllByIsDeletedFalse();

    List<SaleDetail> findAllBySale_SaleIdAndIsDeletedFalse(Long saleId);
}
