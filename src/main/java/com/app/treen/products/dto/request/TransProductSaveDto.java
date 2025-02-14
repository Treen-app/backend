package com.app.treen.products.dto.request;

import com.app.treen.products.entity.Category;
import com.app.treen.products.entity.TransProduct;
import com.app.treen.products.entity.enumeration.Gender;
import com.app.treen.products.entity.enumeration.Method;
import com.app.treen.products.entity.enumeration.Size;
import com.app.treen.products.entity.enumeration.UsedRank;
import com.app.treen.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class TransProductSaveDto {

    private String name;
    private String usedTerm;
    private Size size;
    private String detail;
    private UsedRank usedRank;
    private Method method;
    private Long point;
    private Gender gender;
    private Long categoryId;


    public TransProduct toEntity(User user, Category category) {
        return TransProduct.builder()
                .name(this.name)
                .category(category)
                .user(user)
                .usedRank(this.usedRank)
                .point(this.point)
                .detail(this.detail)
                .gender(this.gender)
                .method(this.method)
                .size(this.size)
                .usedTerm(this.usedTerm)
                .build();
    }


    public static String formatRelativeTime(LocalDateTime createdDate) {
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
