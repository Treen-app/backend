package com.app.treen.mypage.dto;

import com.app.treen.products.entity.enumeration.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionHistoryDto {
    private Long id;
    private String imageUrl;
    private String title;
    private String usedTerm;
    private String grade;
    private String gender;
    private String category;
    private Size size;
    private String tradeMethod;
}
