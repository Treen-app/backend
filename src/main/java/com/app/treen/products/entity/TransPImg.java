package com.app.treen.products.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Table(name = "trans_product_img")
public class TransPImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trans_img_id")
    private Long id;

    @Column(name = "sort_order")
    private int sortOrder = 0;

    @Column(name = "is_main")
    private boolean isMain = false;

    @Column(name = "img_url", nullable = false)
    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trans_product_id")
    private TransProduct transProduct;

    @Builder
    public TransPImg(int sortOrder, boolean isMain, String imgUrl, TransProduct transProduct) {
        this.sortOrder = sortOrder;
        this.isMain = isMain;
        this.imgUrl = imgUrl;
        this.transProduct = transProduct;
    }

}
