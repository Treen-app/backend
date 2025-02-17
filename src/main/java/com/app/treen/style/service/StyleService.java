package com.app.treen.style.service;


import com.app.treen.common.config.S3Uploader;
import com.app.treen.common.response.code.status.ErrorStatus;
import com.app.treen.common.response.exception.CustomException;
import com.app.treen.jpa.repository.style.StyleImageRepository;
import com.app.treen.jpa.repository.style.StyleLikesRepository;
import com.app.treen.jpa.repository.style.StyleRepository;
import com.app.treen.jpa.repository.style.StyleScrapRepository;
import com.app.treen.jpa.repository.user.UserRepository;
import com.app.treen.style.dto.request.StyleSaveRequestDto;
import com.app.treen.style.dto.request.StyleUpdateRequestDto;
import com.app.treen.style.dto.response.StyleListResponseDto;
import com.app.treen.style.dto.response.StyleResponseDto;
import com.app.treen.style.entity.*;
import com.app.treen.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class StyleService {
    private final StyleImageRepository styleImageRepository;
    private final StyleRepository styleRepository;
    private final StyleLikesRepository styleLikesRepository;
    private final StyleScrapRepository styleScrapRepository;

    private final UserRepository userRepository;

    private final S3Uploader s3Uploader;
    private JPAQueryFactory queryFactory;

    // 스타일 등록
    public StyleResponseDto saveStyle(StyleSaveRequestDto requestDto, User user) throws IOException {
        List<MultipartFile> images = requestDto.getStyleImages().getImages();
        if(images.size()<1)
            throw new CustomException(ErrorStatus.IMAGE_MUST_BE_UPLOADED);
        else if(images.size()>5)
            throw new CustomException(ErrorStatus.IMAGE_OVER_UPLOADED);
        List<String> imageUrls = s3Uploader.upload(images, "style-images");
        Style newStyle = styleRepository.save(requestDto.toEntity(user));
        List<StyleImage> styleImages = requestDto.getStyleImages().toImageEntities(newStyle,imageUrls);
        styleImageRepository.saveAll(styleImages);
        return new StyleResponseDto(newStyle,styleImages,user);
    }
    // 스타일 수정
    public StyleResponseDto updateStyle(Long styleId,StyleUpdateRequestDto requestDto,User user) throws IOException {
        Style style = styleRepository.findById(styleId)
                .orElseThrow(()-> new CustomException(ErrorStatus.STYLE_NOT_FOUND));
        style.update(requestDto.getContent());
        styleRepository.save(style);
        List<StyleImage> imageList = styleImageRepository.findByStyle(style);
        return new StyleResponseDto(style,imageList,user);
    }
    // 스타일 삭제
    public void deleteStyle(Long styleId, User user){
        Style style = styleRepository.findById(styleId)
                .orElseThrow(()-> new CustomException(ErrorStatus.STYLE_NOT_FOUND));
        styleImageRepository.deleteByStyle(style);

    }

    // 내가 등록한 스타일 목록 조회
    public List<StyleListResponseDto> viewMyStyle(User user) {
        List<Style> styles = styleRepository.findByUser(user);
        List<StyleListResponseDto> responseDtoList = new ArrayList<>();
        for (Style style : styles) {
            StyleImage mainImage = styleImageRepository.findByStyleAndIsMainTrue(style);
            responseDtoList.add(new StyleListResponseDto(style, style.getUser(), mainImage));
        }
        return responseDtoList;
    }


    // 스타일 검색

    // 스타일 목록 최신순 조회 (팔로우한사람 우선)
    // 스타일 목록 좋아요순 조회

    // 스타일 상세조회
    public StyleResponseDto findById(Long id) {
        Style style = styleRepository.findById(id)
                .orElseThrow(()->new CustomException(ErrorStatus.STYLE_NOT_FOUND));
        List<StyleImage> styleImages = styleImageRepository.findByStyle(style);
        User user = style.getUser();
        return new StyleResponseDto(style,styleImages,user);
    }


    // 스타일 좋아요
    @Transactional
    public boolean likeStyle(Long styleId, User user) {
        Style style = styleRepository.findById(styleId)
                .orElseThrow(() -> new CustomException(ErrorStatus.STYLE_NOT_FOUND));
        boolean isLiked = styleLikesRepository.existsByUserAndStyle(user,style);
        QStyle thisStyle = QStyle.style;
        if (!isLiked){
            styleLikesRepository.save(new StyleLikes(style,user));
            queryFactory.update(thisStyle)
                    .set(thisStyle.likeCount,thisStyle.likeCount.add(1))
                    .where(thisStyle.id.eq(styleId))
                    .execute();
            return true;
        }
        else {
            // 좋아요 취소
            StyleLikes like = styleLikesRepository.findByUserAndStyle(user, style);
            styleLikesRepository.delete(like);
            queryFactory.update(thisStyle)
                    .set(thisStyle.likeCount, thisStyle.likeCount.subtract(1))
                    .where(thisStyle.id.eq(styleId).and(thisStyle.likeCount.gt(0)))
                    .execute();
            return false;
        }
    }

    // 스타일 북마크, 이미 북마크되었으면 취소
    @Transactional
    public boolean scrapStyle(Long styleId, User user) {
        Style style = styleRepository.findById(styleId)
                .orElseThrow(() -> new CustomException(ErrorStatus.STYLE_NOT_FOUND));

        boolean isScrapped = styleScrapRepository.existsByUserAndStyle(user, style);

        if (!isScrapped) {
            styleScrapRepository.save(new StyleScrap(user, style));
            return true;
        } else {
            StyleScrap scrap = styleScrapRepository.findByUserAndStyle(user, style)
                    .orElseThrow(() -> new CustomException(ErrorStatus.SCRAP_NOT_FOUND));
            styleScrapRepository.delete(scrap);
            return false;
        }
    }

    // 내 북마크목록 조회
    public List<StyleListResponseDto> getMyScrapList(User user) {
        List<StyleScrap> scrapList = styleScrapRepository.findByUser(user);
        return scrapList.stream()
                .map(scrap -> new StyleListResponseDto(
                        scrap.getStyle(),
                        scrap.getUser(),
                        styleImageRepository.findByStyleAndIsMainTrue(scrap.getStyle())
                ))
                .collect(Collectors.toList());
    }

    // 스타일 신고
}
