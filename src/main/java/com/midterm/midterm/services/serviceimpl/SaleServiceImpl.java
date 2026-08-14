package com.midterm.midterm.services.serviceimpl;

import com.midterm.midterm.dto.request.SaleItemRequest;
import com.midterm.midterm.dto.request.SaleRequest;
import com.midterm.midterm.dto.response.SaleDetailResponse;
import com.midterm.midterm.dto.response.SaleResponse;
import com.midterm.midterm.entities.Product;
import com.midterm.midterm.entities.Sale;
import com.midterm.midterm.entities.SaleDetail;
import com.midterm.midterm.entities.Staff;
import com.midterm.midterm.exception.ResourceNotFoundException;
import com.midterm.midterm.repository.ProductRepository;
import com.midterm.midterm.repository.SaleRepository;
import com.midterm.midterm.repository.StaffRepository;
import com.midterm.midterm.services.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final StaffRepository staffRepository;

    @Override
    @Transactional
    public SaleResponse create(SaleRequest request) {
        Staff staff = staffRepository.findById(request.getStaffId())
                .orElseThrow(() -> ResourceNotFoundException.notFoundException(
                        "Staff not found with id: " + request.getStaffId()));

        Sale sale = Sale.builder()
                .staff(staff)
                .totalAmount(BigDecimal.ZERO)
                .build();

        List<SaleDetail> saleDetails = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (SaleItemRequest item : request.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> ResourceNotFoundException.notFoundException(
                            "Product not found with id: " + item.getProductId()));

            int quantity = item.getQuantity();
            if (product.getSQty() < quantity) {
                throw new IllegalArgumentException(
                        "Not enough stock for product '" + product.getProductName()
                                + "'. Available: " + product.getSQty());
            }

            product.setSQty(product.getSQty() - quantity);
            product.setSoldQty(product.getSoldQty() + quantity);
            productRepository.save(product);

            BigDecimal unitPrice = product.getPrice();
            BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
            total = total.add(subtotal);

            SaleDetail detail = SaleDetail.builder()
                    .sale(sale)
                    .product(product)
                    .quantity(quantity)
                    .unitPrice(unitPrice)
                    .subtotal(subtotal)
                    .build();

            saleDetails.add(detail);
        }

        sale.setTotalAmount(total);
        sale.setSaleDetails(saleDetails);

        Sale saved = saleRepository.save(sale);

        return toResponse(saved);
    }

    @Override
    public SaleResponse getById(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.notFoundException("Sale not found with id: " + id));
        return toResponse(sale);
    }

    @Override
    public List<SaleResponse> getAll() {
        return saleRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<SaleResponse> getByStaff(Long staffId) {
        if (!staffRepository.existsById(staffId)) {
            throw ResourceNotFoundException.notFoundException("Staff not found with id: " + staffId);
        }
        return saleRepository.findByStaff_Sid(staffId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private SaleResponse toResponse(Sale sale) {
        List<SaleDetailResponse> details = sale.getSaleDetails().stream()
                .map(d -> SaleDetailResponse.builder()
                        .saleDetailId(d.getSaleDetailId())
                        .productId(d.getProduct().getPid())
                        .productName(d.getProduct().getProductName())
                        .quantity(d.getQuantity())
                        .unitPrice(d.getUnitPrice())
                        .subtotal(d.getSubtotal())
                        .build())
                .toList();

        return SaleResponse.builder()
                .saleId(sale.getSaleId())
                .saleDate(sale.getSaleDate())
                .totalAmount(sale.getTotalAmount())
                .staffId(sale.getStaff().getSid())
                .staffName(sale.getStaff().getUserName())
                .details(details)
                .build();
    }
}