package com.app.treen.products.dto.request;

import com.app.treen.products.entity.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class TransRegionRequestDto {
    private List<Long> regionIds;

    public TransRegionRequestDto(List<Long> regionIds) {
        this.regionIds = regionIds;
    }

    public List<TransRegion> toRegionEntities(TransProduct transProduct, List<Region> regions) {
        if (transProduct == null) {
            throw new IllegalArgumentException("TradeProduct cannot be null");
        }

        List<TransRegion> transRegions = new ArrayList<>();
        for (Region region : regions) {
            transRegions.add(TransRegion.builder()
                    .transProduct(transProduct)
                    .region(region)
                    .build());
        }
        return transRegions;
    }
}
