package com.app.treen.style.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class StyleResponseDto {
    Long sytleId;
    LocalDateTime createdDate;
    String content;

    List<String> imageList;

    // userProfile
    String

}
