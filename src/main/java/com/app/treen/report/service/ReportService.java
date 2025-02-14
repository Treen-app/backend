package com.app.treen.report.service;

import com.app.treen.jpa.repository.report.ReportRepository;
import com.app.treen.jpa.repository.user.UserRepository;
import com.app.treen.report.dto.ReportRequestDto;
import com.app.treen.report.entity.Report;
import com.app.treen.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createReport(ReportRequestDto dto) {
        User reporter = userRepository.findByLoginId(dto.getReporterLoginId())
                .orElseThrow(() -> new IllegalArgumentException("신고한 사용자를 찾을 수 없습니다."));

        User reportedUser = null;
        if (dto.getReportedUserLoginId() != null) {
            reportedUser = userRepository.findByLoginId(dto.getReportedUserLoginId())
                    .orElseThrow(() -> new IllegalArgumentException("신고된 사용자를 찾을 수 없습니다."));
        }

        Report report = Report.builder()
                .reporter(reporter)
                .reportedUser(reportedUser)
                .reason(dto.getReason())
                .otherReason(dto.getOtherReason())
                .build();

        reportRepository.save(report);

        // 신고된 사용자가 있으면 신고 횟수 계산 후 정지
        if (reportedUser != null) {
            long reportCount = reportRepository.countByReportedUser(reportedUser);
            if (reportCount >= 3) {
                reportedUser.changeStatusToSuspend();
                userRepository.save(reportedUser);
            }
        }
    }
}