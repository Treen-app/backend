package com.app.treen.report.entity;

public enum ReportReason {
    PROHIBITED_ITEM,      // 판매 금지 물품
    NOT_SECOND_HAND,      // 중고거래 게시글이 아님
    FRAUD_POST,           // 사기 글
    OTHER,                // 기타 사유
    INAPPROPRIATE_USER    // 부적절한 사용자 신고
}
