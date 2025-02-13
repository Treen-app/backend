package com.app.treen.products.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.app.treen.products.entity.*;
import com.app.treen.products.entity.enumeration.*;
import com.app.treen.user.entity.User;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL) // ✅ NULL인 필드는 JSON에서 제외
public class TransProductResponseDto {

    private Long id;
    private String name;
    private String usedTerm;
    private String detail;
    private Gender gender;
    private Size size;
    private UsedRank usedRank;
    private Long point;
    private Method method;
    private String category;
    private Long viewCount;
    private Long likedCount;
    private List<String> imageUrls;
    private List<String> regions;

    private String userImage;
    private String userName;
    private String createdDate;

    private Long writerId;

    public TransProductResponseDto(TransProduct transProduct, List<TransRegion> transRegions, List<TransPImg> images, User user) {
        this.id = transProduct.getId();
        this.name = transProduct.getName();
        this.usedTerm = transProduct.getUsedTerm();
        this.detail = transProduct.getDetail();
        this.gender = transProduct.getGender();
        this.size = transProduct.getSize();
        this.usedRank = transProduct.getUsedRank();
        this.point = transProduct.getPoint();
        this.method = transProduct.getMethod();
        this.category = transProduct.getCategory().getName();
        this.viewCount = transProduct.getViewCount();
        this.likedCount = transProduct.getLikedCount();

        // ✅ 이미지 URL 리스트 설정
        this.imageUrls = images.stream()
                .map(TransPImg::getImgUrl)
                .collect(Collectors.toList());

        // ✅ 지역 리스트 설정 (null이면 설정 안 함)
        if (transRegions != null && !transRegions.isEmpty()) {
            this.regions = transRegions.stream()
                    .map(transRegion -> transRegion.getRegion().getName())
                    .collect(Collectors.toList());
        }

        // ✅ 유저 정보 설정
        this.userName = user.getUserName();
        this.userImage = user.getProfileImgUrl();
        this.writerId = user.getId();

        // ✅ 생성 시간 포맷팅
        this.createdDate = formatRelativeTime(transProduct.getCreatedDate());
    }

    /*
     * 상대적인 시간 표현으로 변환
     * @param createdDate 생성 시간
     * @return 상대적 시간
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
