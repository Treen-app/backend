package com.app.treen.style.dto.request;

import com.app.treen.style.entity.Style;
import com.app.treen.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
public class StyleSaveRequestDto {

    String content;
    StyleImageRequestDto  styleImages;

    public Style toEntity(User user) {
        return Style.builder()
                .content(content)
                .user(user)
                .build();
    }

}
