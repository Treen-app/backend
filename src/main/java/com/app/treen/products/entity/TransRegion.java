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
@Table(name="trans_regions")
public class TransRegion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trans_region_id")
    private Long id;

    @ManyToOne @JoinColumn(name = "trans_product_id")
    private TransProduct transProduct;

    @ManyToOne @JoinColumn(name = "region_id")
    private Region region;
}
