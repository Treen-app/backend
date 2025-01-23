package com.app.treen.products.dto.response;

import com.app.treen.products.entity.*;
import com.app.treen.products.entity.enumeration.*;
import com.app.treen.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class TradeProductResponseDto {

    private Long id;
    private String name;
    private String usedTerm;
    private String detail;
    private Gender gender;
    private Size size;
    private UsedRank usedRank;
    private Method method;
    private String category;
    private Long viewCount;
    private Long likedCount;

    private Size wishSize;
    private String wishColor;
    private List<String> imageUrls;
    private List<String> wishCategories;
    private List<String> regions;
    private Status status;
    private TradeType tradeType;

    private String userImage; // 유저 사진

    private String userName;
    private String createdDate; // 상대적 시간 표현

    private Long writerId;

    public TradeProductResponseDto(TradeProduct tradeProduct, List<TradeRegion> regions, List<TradePImg> images,List<WishCategory> wishCategories,User user) {
        this.id = tradeProduct.getId();
        this.name = tradeProduct.getName();
        this.usedTerm = tradeProduct.getUsedTerm();
        this.detail = tradeProduct.getDetail();
        this.gender = tradeProduct.getGender();
        this.size = tradeProduct.getSize();
        this.usedRank = tradeProduct.getUsedRank();
        this.method = tradeProduct.getMethod();
        this.category = tradeProduct.getCategory().getName();
        this.viewCount = tradeProduct.getViewCount();
        this.likedCount = tradeProduct.getLikedCount();
        this.wishSize = tradeProduct.getWishSize();
        this.wishColor = tradeProduct.getWishColor();
        this.imageUrls = images.stream()
                .map(TradePImg::getImgUrl)
                .collect(Collectors.toList());
        if (wishCategories != null) {
            this.wishCategories = wishCategories.stream()
                    .map(wishCategory -> wishCategory.getCategory().getName())
                    .collect(Collectors.toList());
        } else {
            this.wishCategories = Collections.emptyList(); // 빈 리스트로 초기화
        }

        if (regions != null) {
            this.regions = regions.stream()
                    .map(tradeRegion -> tradeRegion.getRegion().getName())
                    .collect(Collectors.toList());
        } else {
            this.regions = Collections.emptyList(); // 빈 리스트로 초기화
        }
        this.status = tradeProduct.getTransactionStatus();
        this.tradeType = tradeProduct.getTradeType();

        // 유저 정보
        this.userImage = user.getProfileImgUrl();
        this.userName = user.getUserName();
        this.writerId = user.getId();

        // createdDate 포맷팅
        this.createdDate = formatRelativeTime(tradeProduct.getCreatedDate());
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
