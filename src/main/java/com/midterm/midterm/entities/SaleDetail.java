package com.midterm.midterm.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "sale_detail")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"sale", "product"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SaleDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_detail_id")
    @EqualsAndHashCode.Include
    private Long saleDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "sale_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_sale_detail_sale")
    )
    private Sale sale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "product_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_sale_detail_product")
    )
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "subtotal", nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;
}
