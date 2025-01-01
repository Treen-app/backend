package com.app.treen.products.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "trade_product_img")
public class TradePImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trade_img_id")
    private Long id;

    @Column(name = "sort_order")
    private int sortOrder = 0;

    @Column(name = "is_main")
    private boolean isMain = false;

}