package com.app.treen.trade.entity;

import com.app.treen.products.entity.TradeProduct;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offered_product_id")
    private Long id;

    // 판매자의 교환 수락 여부
    private boolean isAccepted;

    // 교환상품(교환 신청자 측 상품)
    @ManyToOne
    @JoinColumn(name = "trade_product_id", nullable = false)
    private TradeProduct product;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "trade_offer_id", nullable = false)
    private TradeOffer tradeOffer;

}
