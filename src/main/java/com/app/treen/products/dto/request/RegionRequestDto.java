package com.app.treen.products.dto.request;

import com.app.treen.products.entity.Region;
import com.app.treen.products.entity.TradeProduct;
import com.app.treen.products.entity.TradeRegion;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class RegionRequestDto {
    private List<Long> regionIds;

    public RegionRequestDto(List<Long> regionIds) {
        this.regionIds = regionIds;
    }

    public List<TradeRegion> toRegionEntities(TradeProduct tradeProduct, List<Region> regions) {
        if (tradeProduct == null) {
            throw new IllegalArgumentException("TradeProduct cannot be null");
        }
        if (regions == null || regions.isEmpty()) {
            throw new IllegalArgumentException("Regions cannot be null or empty");
        }

        List<TradeRegion> tradeRegions = new ArrayList<>();
        for (Region region : regions) {
            tradeRegions.add(TradeRegion.builder()
                    .tradeProduct(tradeProduct)
                    .region(region)
                    .build());
        }
        return tradeRegions;
    }
}
