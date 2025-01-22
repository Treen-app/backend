package com.app.treen.products.entity;

import com.app.treen.BaseTimeEntity;
import com.app.treen.products.entity.enumeration.*;
import com.app.treen.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="trans_products")
public class TransProduct extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trans_product_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "used_term", nullable = true)
    private String usedTerm;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String detail;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Size size;

    @Enumerated(EnumType.STRING)
    @Column(name = "used_rank")
    private UsedRank usedRank;

    @Builder.Default
    @Column(nullable = false)
    private Long point = 0L;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Method method = Method.ALL;

    @Builder.Default
    @Column(name = "view_count")
    private Long viewCount = 0L;

    @Builder.Default
    @Column(name = "liked_count")
    private Long likedCount = 0L;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_status")
    private Status transactionStatus = Status.BEFORE;

    @Builder
    public TransProduct(User user, String name, Category category, String usedTerm,
                        String detail, Gender gender, Size size, UsedRank usedRank,
                        Long point, Method method, Status transactionStatus) {
        this.user = user;
        this.name = name;
        this.category = category;
        this.usedTerm = usedTerm;
        this.detail = detail;
        this.gender = gender;
        this.size = size;
        this.usedRank = usedRank;
        this.point = point != null ? point : 0L;
        this.method = method != null ? method : Method.ALL;
        this.transactionStatus = transactionStatus != null ? transactionStatus : Status.BEFORE;
    }

    public void updateDetails(
            String name,
            String usedTerm,
            String detail,
            Gender gender,
            Size size,
            UsedRank usedRank,
            Long point,
            Method method,
            Category category) {

        this.name = name;
        this.usedTerm = usedTerm;
        this.detail = detail;
        this.gender = gender;
        this.size = size;
        this.usedRank = usedRank;
        this.point = point;
        this.method = method;
        this.category = category;
    }
}