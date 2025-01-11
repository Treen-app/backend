package com.app.treen.products.dto.response;

import com.app.treen.products.entity.TransPImg;
import com.app.treen.products.entity.TransProduct;
import com.app.treen.products.entity.enumeration.Method;
import com.app.treen.products.entity.enumeration.Size;
import com.app.treen.products.entity.enumeration.Status;
import com.app.treen.products.entity.enumeration.UsedRank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TransResponseListDto {

    private Long id; // 상품 ID
    private String representativeImage; // 대표 사진
    private String name; // 상품명
    private String category; // 카테고리
    private String createdDate; // 게시일자 (n시간 전, n일 전)
    private Long likedCount; // 좋아요 수
    private Long point; // 포인트
    private Status status; // 거래 상태
    private Size size; // 사이즈
    private Method method; // 거래 방법
    private UsedRank usedRank; // 사용

    public TransResponseListDto(TransProduct transProduct) {
        this.id = transProduct.getId(); // 상품 ID

        // 대표 사진 (첫 번째 이미지를 대표 사진으로 설정)
        this.representativeImage = transProduct.getImages().stream()
                .map(TransPImg::getImgUrl)
                .findFirst()
                .orElse(null);

        this.name = transProduct.getName(); // 상품명
        this.category = transProduct.getCategory().getName(); // 카테고리
        this.likedCount = transProduct.getLikedCount(); // 좋아요 수
        this.point = transProduct.getPoint(); // 포인트
        this.status = transProduct.getTransactionStatus();
        this.method = transProduct.getMethod();
        this.size = transProduct.getSize();
        this.usedRank = transProduct.getUsedRank();

        // 게시일자 포맷팅
        this.createdDate = formatRelativeTime(transProduct.getCreatedDate());
    }

    /**
     * 상대적인 시간 표현으로 변환
     * @param createdDate 생성 시간
     * @return 상대적 시간 (예: "5분 전", "3시간 전")
     */
    private String formatRelativeTime(LocalDateTime createdDate) {
        Duration duration = Duration.between(createdDate, LocalDateTime.now());
        long seconds = duration.getSeconds();
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (seconds < 60) {
            return seconds + "초 전";
        } else if (minutes < 60) {
            return minutes + "분 전";
        } else if (hours < 24) {
            return hours + "시간 전";
        } else {
            return days + "일 전";
        }
    }
}
