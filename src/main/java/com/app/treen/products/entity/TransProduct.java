package com.app.treen.products.entity;

import com.app.treen.BaseTimeEntity;
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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trans_product_id")
    private Long id;

    @ManyToOne @JoinColumn(name = "user_id")
    private User user;

    @OneToOne @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "used_term", nullable = true)
    private String usedTerm;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String detail;

    @Enumerated(EnumType.STRING)
    private Size size;

    @Enumerated(EnumType.STRING)
    @Column(name = "used_rank")
    private UsedRank usedRank;

    private Long point = 0L;

    @Enumerated(EnumType.STRING)
    private Method method;


    @Column(name = "view_count")
    private Long viewCount;

    @Column(name = "liked_count")
    private Long likedCount = 0L;

}
