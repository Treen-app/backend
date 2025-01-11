package com.app.treen.products.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Table(name = "trade_product_img")
public class TradePImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trade_img_id")
    private Long id;

    @Column(name = "img_url", nullable = false)
    private String imgUrl;

    @Builder.Default
    @Column(name = "sort_order")
    private int sortOrder = 0;

    @Builder.Default
    @Column(name = "is_main")
    private boolean isMain = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_product_id", nullable = false)
    private TradeProduct tradeProduct;



}