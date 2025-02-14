package com.app.treen.style.dto.response;

import com.app.treen.style.entity.Style;
import com.app.treen.style.entity.StyleImage;
import com.app.treen.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class StyleResponseDto {
    Long styleId;
    String createdDate;
    String content;
    List<String> imageList;

    Long viewCount;
    Long likeCount;

    // userProfile
    Long userId;
    String userProfileImage;
    String userName;

    @Builder
    public StyleResponseDto(Style style, List<StyleImage> images, User user) {
        this.styleId = style.getId();
        this.content = style.getContent();
        this.imageList = images.stream()
                .map(StyleImage::getImageUrl)
                .collect(Collectors.toList());
        this.viewCount = style.getViewCount();
        this.likeCount = style.getLikeCount();
        this.userId = user.getId();
        this.userName = user.getUserName();
        this.userProfileImage = user.getProfileImgUrl();
        this.createdDate = formatRelativeTime(style.getCreatedDate());
    }


    /**
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
