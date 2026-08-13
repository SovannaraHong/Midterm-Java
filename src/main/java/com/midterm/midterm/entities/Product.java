package com.midterm.midterm.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pid")
    private Long pid;

    @Column(name = "pname", nullable = false, length = 150)
    private String productName;

    @Column(name = "image_url", nullable = false, length = 150)
    private String imageUrl;
    @Column(name = "description", nullable = false, length = 200)
    private String description;


    @Column(name = "sqty", nullable = false)
    private Integer sQty;

    @Column(name = "price", nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(name = "expired_date")
    private LocalDate expiredDate;

    @Column(name = "sold_qty", nullable = false)
    @Builder.Default
    private Integer soldQty = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_id", nullable = false, foreignKey = @ForeignKey(name = "fk_product_category"))
    private Category category;
}