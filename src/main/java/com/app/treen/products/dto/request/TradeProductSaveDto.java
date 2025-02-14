package com.app.treen.products.dto.request;

import com.app.treen.products.entity.*;
import com.app.treen.products.entity.enumeration.*;
import com.app.treen.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class TradeProductSaveDto {

    private String name;
    private String usedTerm;
    private Size size;
    private String detail;
    private UsedRank usedRank;
    private Method method;
    private Gender gender;
    private String wishColor;
    private Size wishSize;
    private TradeType tradeType;
    private Long categoryId;



    public TradeProduct toEntity(User user, Category category) {
        return TradeProduct.builder()
                .name(this.name)
                .user(user)
                .usedRank(this.usedRank)
                .detail(this.detail)
                .gender(this.gender)
                .method(this.method)
                .size(this.size)
                .usedTerm(this.usedTerm)
                .wishColor(this.wishColor)
                .wishSize(this.wishSize)
                .tradeType(this.tradeType)
                .category(category)
                .build();
    }


}
