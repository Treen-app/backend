package com.app.treen.report.dto;

import com.app.treen.report.entity.ReportReason;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportRequestDto {
    @NotNull
    private String reporterLoginId; // 신고한 사용자 ID

    private String reportedUserLoginId; // 신고된 사용자 ID (사용자 신고일 경우)

    @NotNull
    private ReportReason reason; // 신고 사유

    private String otherReason; // 기타 사유 (선택 사항)
}