package com.app.treen.jpa.repository.report;

import com.app.treen.report.entity.Report;
import com.app.treen.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
    long countByReportedUser(User reportedUser); // 신고 횟수 조회 기능 추가
}
