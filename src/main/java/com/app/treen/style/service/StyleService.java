package com.app.treen.style.service;


import com.app.treen.common.config.S3Uploader;
import com.app.treen.common.response.code.status.ErrorStatus;
import com.app.treen.common.response.exception.CustomException;
import com.app.treen.jpa.repository.style.StyleImageRepository;
import com.app.treen.jpa.repository.style.StyleLikesRepository;
import com.app.treen.jpa.repository.style.StyleRepository;
import com.app.treen.jpa.repository.style.StyleScrapRepository;
import com.app.treen.style.dto.request.StyleSaveRequestDto;
import com.app.treen.style.dto.request.StyleUpdateRequestDto;
import com.app.treen.style.dto.response.StyleListResponseDto;
import com.app.treen.style.dto.response.StyleResponseDto;
import com.app.treen.style.entity.Style;
import com.app.treen.style.entity.StyleImage;
import com.app.treen.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class StyleService {
    private final StyleImageRepository styleImageRepository;
    private final StyleRepository styleRepository;
    private final StyleLikesRepository styleLikesRepository;
    private final StyleScrapRepository styleScrapRepository;

    private final S3Uploader s3Uploader;

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
        List<Style> style = styleRepository.findByUser(user);
        StyleImage mainImage = styleImageRepository.findByStyleAndIsMainTrue(style);
        List<StyleListResponseDto> responseDto = style.stream()
                .map(s -> new StyleListResponseDto(
                        s,
                        s.getUser(),
                        mainImage
                ))
                .collect(Collectors.toList());
        return responseDto;
    }

    // 스타일 검색

    // 스타일 목록 최신순 조회 (팔로우한사람 우선)
    // 스타일 목록 좋아요순 조회
    // 스타일 상세조회
    // 스타일 좋아요
    // 스타일 북마크
    // 내 북마크목록 조회
    // 스타일 신고

}
