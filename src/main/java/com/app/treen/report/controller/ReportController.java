package com.app.treen.report.controller;

import com.app.treen.report.dto.ReportRequestDto;
import com.app.treen.report.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    // 신고 접수
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<String> createReport(@Valid @RequestBody ReportRequestDto reportRequestDto) {
        reportService.createReport(reportRequestDto);
        return ResponseEntity.ok("신고가 접수되었습니다.");
    }
}
