package com.midterm.midterm.services.serviceimpl;

import com.midterm.midterm.dto.response.SaleDetailResponse;
import com.midterm.midterm.entities.SaleDetail;
import com.midterm.midterm.exception.ResourceNotFoundException;
import com.midterm.midterm.repository.SaleDetailRepository;
import com.midterm.midterm.services.SaleDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SaleDetailServiceImpl implements SaleDetailService {

    private final SaleDetailRepository saleDetailRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SaleDetailResponse> getAll() {

        return saleDetailRepository.findAllByIsDeletedFalse()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SaleDetailResponse getById(Long id) {

        SaleDetail detail = saleDetailRepository
                .findBySaleDetailIdAndIsDeletedFalse(id)
                .orElseThrow(() ->
                        ResourceNotFoundException.notFoundException(
                                "Sale detail not found with id: " + id
                        ));

        return toResponse(detail);
    }

    @Override
    public void delete(Long id) {

        SaleDetail detail = saleDetailRepository
                .findBySaleDetailIdAndIsDeletedFalse(id)
                .orElseThrow(() ->
                        ResourceNotFoundException.notFoundException(
                                "Sale detail not found with id: " + id
                        ));

        detail.setIsDeleted(true);
        saleDetailRepository.save(detail);
    }

    private SaleDetailResponse toResponse(SaleDetail detail) {

        return SaleDetailResponse.builder()
                .saleDetailId(detail.getSaleDetailId())
                .productId(detail.getProduct().getPid())
                .productName(detail.getProduct().getProductName())
                .quantity(detail.getQuantity())
                .unitPrice(detail.getUnitPrice())
                .subtotal(detail.getSubtotal())
                .build();
    }
}
