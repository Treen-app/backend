package com.app.treen.products.entity;

import com.app.treen.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "trade_likes")
public class TradeLikes {
    @Builder
    public TradeLikes(User user, TradeProduct tradeProduct) {
        this.user = user;
        this.tradeProduct = tradeProduct;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "trade_product_id")
    private TradeProduct tradeProduct;
}
