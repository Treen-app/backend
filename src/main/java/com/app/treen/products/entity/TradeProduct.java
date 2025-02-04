package com.app.treen.products.entity;

import com.app.treen.BaseTimeEntity;
import com.app.treen.products.dto.request.TradeProductUpdateDto;
import com.app.treen.products.entity.enumeration.*;
import com.app.treen.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "trade_products")
public class TradeProduct extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trade_product_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;

    @Column(nullable = false)
    private String name;

    @Column(name = "used_term", nullable = true)
    private String usedTerm;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String detail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Size size;

    @Enumerated(EnumType.STRING)
    @Column(name = "used_rank")
    private UsedRank usedRank;

    @Enumerated(EnumType.STRING)
    private Method method;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "wish_color", nullable = true)
    private String wishColor;

    @Enumerated(EnumType.STRING)
    @Column(name = "wish_size", nullable = true)
    private Size wishSize;

    @Builder.Default
    @Column(name = "view_count", nullable = false)
    private Long viewCount = 0L;

    @Builder.Default
    @Column(name = "liked_count", nullable = false)
    private Long likedCount = 0L;


    @Enumerated(EnumType.STRING)
    @Column(name = "trade_type", nullable = false)
    private TradeType tradeType;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_status")
    Status transactionStatus = Status.BEFORE;


    public void updateDetail(TradeProductUpdateDto dto, Category category) {
        this.name = dto.getName();
        this.usedTerm = dto.getUsedTerm();
        this.size = dto.getSize();
        this.detail = dto.getDetail();
        this.usedRank = dto.getUsedRank();
        this.method = dto.getMethod();
        this.gender = dto.getGender();
        this.wishColor = dto.getWishColor();
        this.wishSize = dto.getWishSize();
        this.tradeType = dto.getTradeType();
        this.category = category;
    }



}
