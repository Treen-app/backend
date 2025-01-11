package com.app.treen.products.dto.response;

import com.app.treen.products.entity.*;
import com.app.treen.products.entity.enumeration.*;
import com.app.treen.user.entity.User;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
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

    public TransProductResponseDto(TransProduct transProduct, List<TransRegion> transRegions, User user) {
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

        this.imageUrls = transProduct.getImages().stream()
                .map(TransPImg::getImgUrl)
                .collect(Collectors.toList());

        this.regions = transRegions.stream()
                .map(transRegion -> transRegion.getRegion().getName())
                .collect(Collectors.toList());

        // 유저 정보 반환
        this.userName = user.getUserName();
        this.userImage = user.getProfileImgUrl();
        this.writerId = user.getId();

        // createdDate 포맷팅
        this.createdDate = formatRelativeTime(transProduct.getCreatedDate());
    }

    /*
     * 상대적인 시간 표현으로 변환
     * @param createdDate 생성 시간
     * @return 상대적 시간
     * /

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
