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
@Table(name="trade_regions")
public class TradeRegion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trade_region_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trade_product_id")
    private TradeProduct tradeProduct;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

}
