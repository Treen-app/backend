package com.app.treen.style.dto.response;

import com.app.treen.style.entity.Style;
import com.app.treen.style.entity.StyleImage;
import com.app.treen.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StyleListResponseDto {

    private Long styleId;
    private String mainImg;
    // 좋아요 수
    private Long likeCount;
    // 게시일자
    private LocalDateTime createdDate;

    // userprofile
    private Long userId;
    private String userName;


    @Builder
    public StyleListResponseDto(Style style, User user, StyleImage styleImage) {
        this.styleId = style.getId();
        this.mainImg = styleImage.getImageUrl();
        this.likeCount = style.getLikeCount();
        this.createdDate = style.getCreatedDate();
        this.userId = user.getId();
        this.userName = user.getUserName();
    }

}
