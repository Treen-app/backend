package com.app.treen.products.entity;

import com.app.treen.BaseTimeEntity;
import com.app.treen.products.dto.request.TransProductUpdateDto;
import com.app.treen.products.entity.enumeration.*;
import com.app.treen.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private String name;

    @OneToOne @JoinColumn(name = "category_id")
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
    Status transactionStatus = Status.BEFORE;


    @OneToMany(mappedBy = "transProduct", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransPImg> images;


    public void updateDetails(TransProductUpdateDto dto, Category category) {
        this.name = dto.getName();
        this.usedTerm = dto.getUsedTerm();
        this.detail = dto.getDetail();
        this.gender = dto.getGender();
        this.size = dto.getSize();
        this.usedRank = dto.getUsedRank();
        this.point = dto.getPoint();
        this.method = dto.getMethod();
        this.category = category;

        // 이미지 업데이트
        updateImages(dto.getImageUrls());
    }

    private void updateImages(List<String> imageUrls) {
        // 기존 이미지 목록 초기화 (orphanRemoval=true 설정 덕분에 자동 삭제)
        this.images.clear();

        // 새로운 이미지 목록 추가
        for (int i = 0; i < imageUrls.size(); i++) {
            this.images.add(TransPImg.builder()
                    .transProduct(this)
                    .imgUrl(imageUrls.get(i))
                    .sortOrder(i)
                    .isMain(i == 0) // 첫 번째 이미지를 대표 이미지로 설정
                    .build());
        }
    }

}
