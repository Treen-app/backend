package com.app.treen.products.entity;

import com.app.treen.BaseTimeEntity;
import com.app.treen.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

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
    @Column(name = "wish_size")
    private Size wishSize;

    @Column(name = "view_count", nullable = false)
    private Long viewCount = 0L;

    @Column(name = "liked_count", nullable = false)
    private Long likedCount = 0L;

    @ManyToMany
    @JoinTable(
            name = "tradeWishCategories",
            joinColumns = @JoinColumn(name = "trade_product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();


}
