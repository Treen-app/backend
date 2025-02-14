package com.app.treen.mypage.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TradeHistoryDto {
    private Long id;
    private String imageUrl;
    private String title;
    private String usedTerm;
    private String grade;
    private String gender;
    private String category;
    private String size;
    private String tradeMethod;
}
