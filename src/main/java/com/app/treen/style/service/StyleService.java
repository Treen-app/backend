package com.app.treen.style.service;


import com.app.treen.jpa.repository.style.StyleImageRepository;
import com.app.treen.jpa.repository.style.StyleLikesRespository;
import com.app.treen.jpa.repository.style.StyleRepository;
import com.app.treen.jpa.repository.style.StyleScrapRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StyleService {
    private final StyleImageRepository styleImageRepository;
    private final StyleRepository styleRepository;
    private final StyleLikesRespository styleLikesRespository;
    private final StyleScrapRepository styleScrapRepository;
    // 스타일 등록
    // 스타일 수정
    // 스타일 삭제
    // 내가 등록한 스타일 조회
    // 특정 사용자의 스타일 조회
    // 스타일 검색
    // 스타일 목록 조회 (팔로우한사람 우선)
    // 스타일 상세조회
    // 스타일 신고
}
