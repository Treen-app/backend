package com.app.treen.style.dto.request;

import com.app.treen.style.entity.Style;
import com.app.treen.style.entity.StyleReport;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StyleReportRequestDto {
    private String content;

    public StyleReportRequestDto(String content) {
        this.content = content;
    }

}
