package com.app.treen.products.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="wish_categories")
public class WishCategory {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "trade_product_id")
    private TradeProduct tradeProduct;

    public void setTradeProduct(TradeProduct tradeProduct) {
        this.tradeProduct = tradeProduct;
    }

}
