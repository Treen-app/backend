package com.app.treen.trade.entity;

import com.app.treen.BaseTimeEntity;
import com.app.treen.products.entity.TradeProduct;
import com.app.treen.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeOffer extends BaseTimeEntity { // 자유교환 신청정보

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trade_offer_id")
    private Long id;

    // 승인상품(판매자 측 상품)
    @ManyToOne
    @JoinColumn(name = "trade_product_id", nullable = false)
    private TradeProduct salesProduct;

    // 교환 요청자
    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    private User buyer;

    @OneToMany(mappedBy = "tradeOffer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OfferedProduct> offeredProductList = new ArrayList<>();

}
