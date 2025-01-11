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
public class TransPImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trans_img_id")
    private Long id;

    @Builder.Default
    @Column(name = "sort_order")
    private int sortOrder = 0;

    @Builder.Default
    @Column(name = "is_main")
    private boolean isMain = false;

    @Column(name = "img_url", nullable = false)
    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trans_product_id")
    private TransProduct transProduct;

}
