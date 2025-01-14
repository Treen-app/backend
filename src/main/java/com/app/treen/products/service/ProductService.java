package com.app.treen.products.service;

import com.app.treen.common.config.S3Uploader;
import com.app.treen.common.response.code.status.ErrorStatus;
import com.app.treen.products.dto.ProductQueryHelper;
import com.app.treen.products.dto.TradeQueryHelper;
import com.app.treen.products.dto.request.TradeProductSaveDto;
import com.app.treen.products.dto.request.TradeProductUpdateDto;
import com.app.treen.products.dto.request.TransProductSaveDto;
import com.app.treen.products.dto.request.TransProductUpdateDto;
import com.app.treen.products.dto.response.TradeProductResponseDto;
import com.app.treen.products.dto.response.TradeResponseListDto;
import com.app.treen.products.dto.response.TransProductResponseDto;
import com.app.treen.products.dto.response.TransResponseListDto;
import com.app.treen.products.entity.*;
import com.app.treen.products.entity.enumeration.TradeType;
import com.app.treen.products.entity.repository.*;
import com.app.treen.user.entity.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final TransProductRepository transProductRepository;
    private final TradeProductRepository tradeProductRepository;

    private final TransPImgRepository transPImgRepository;

    private final CategoryRepository categoryRepository;
    private final TradePImgRepository tradePImgRepository;

    private final WishCategoryRepository wishCategoryRepository;

    private final TransRegionRepository transRegionRepository;

    private final RegionRepository regionRepository;

    private final TradeRegionRepository tradeRegionRepository;
    private final S3Uploader s3Uploader;

    private final TransLikesRepository transLikesRepository;
    private final TradeLikesRepository tradeLikesRepository;

    private JPAQueryFactory queryFactory;

    // 1 . 상품 거래 등록
    @Transactional
    public TransProductResponseDto saveTransProduct(TransProductSaveDto dto, List<MultipartFile> files, User user) throws IOException {
        // S3에 파일 업로드
        List<String> uploadedUrls = s3Uploader.upload(files, "trans-product-images");
        dto.setImageUrls(uploadedUrls);

        // Category 조회
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));

        // TransProduct 저장
        TransProduct transProduct = transProductRepository.save(dto.toEntity(user, category));

        // 이미지 저장
        List<TransPImg> images = dto.toImageEntities(transProduct);
        transPImgRepository.saveAll(images);

        // Region 리스트 조회
        List<Region> regions = regionRepository.findAllById(dto.getRegionIds());

        // regions가 비어있으면 null로 처리
        List<TransRegion> transRegions = null;
        if (regions.isEmpty()) {
            // null을 저장
            transRegionRepository.saveAll(Collections.singletonList(null)); // null 저장 예시
        } else {
            // TransRegion 객체 생성
            transRegions = dto.toRegionEntities(transProduct, regions);

            // TransRegion 저장
            transRegionRepository.saveAll(transRegions);
        }

        return new TransProductResponseDto(transProduct, transRegions, user);

    }

    // 2. 상품 교환 등록
    @Transactional
    public TradeProductResponseDto saveTradeProduct(TradeProductSaveDto dto, List<MultipartFile> files, User user) throws IOException {
        // 1. S3에 파일 업로드 및 URL 변환
        List<String> storedFiles = s3Uploader.upload(files, "trade-product-images");
        dto.setImageUrls(storedFiles);

        // 2. Category 조회
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));

        // 3. TradeProduct 저장
        TradeProduct tradeProduct = tradeProductRepository.save(dto.toEntity(user, category));

        // 4. 이미지 저장
        List<TradePImg> images = dto.toImageEntities(tradeProduct);
        tradePImgRepository.saveAll(images);

        // 5. 희망 카테고리 저장
        if (dto.getWishCategoryIds() != null && !dto.getWishCategoryIds().isEmpty()) {
            List<Category> wishCategories = categoryRepository.findAllById(dto.getWishCategoryIds());
            List<WishCategory> wishCategoryEntities = dto.toWishCategoryEntities(tradeProduct, wishCategories);
            wishCategoryRepository.saveAll(wishCategoryEntities);
        }

        // 6. 지역 정보 저장
        List<TradeRegion> tradeRegions = new ArrayList<>();
        List<Region> regions = new ArrayList<>();
        if (dto.getRegionIds() != null && !dto.getRegionIds().isEmpty()) {
            regions = regionRepository.findAllById(dto.getRegionIds());

            // regions가 비어있지 않으면 TransRegion 생성
            if (!regions.isEmpty()) {
                tradeRegions = dto.toRegionEntities(tradeProduct, regions);
                tradeRegionRepository.saveAll(tradeRegions);
            }
        } else {
            // 만약 dto.getRegionIds()가 null이거나 비어 있으면, tradeRegions를 null로 설정하거나 적절한 값으로 처리
            tradeRegions = null; // 또는 빈 리스트를 설정: tradeRegions = new ArrayList<>();
        }

        // 7. 응답 DTO 생성 및 반환
        return new TradeProductResponseDto(tradeProduct, tradeRegions, user);

    }

    // 3. 거래 상품 수정
    @Transactional
    public void updateTransProduct(Long productId, TransProductUpdateDto dto, List<MultipartFile> files) throws IOException
    {
        TransProduct existingProduct = transProductRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));


        // S3에 새로운 파일 업로드 및 URL 변환
        if (files != null && !files.isEmpty()) {
            List<String> uploadedUrls = s3Uploader.upload(files, "trans-product-images");
            dto.setImageUrls(uploadedUrls);

            // 기존 이미지 삭제 및 새로운 이미지 저장
            transPImgRepository.deleteByTransProduct(existingProduct);
            List<TransPImg> newImages = dto.toImageEntities(existingProduct);
            transPImgRepository.saveAll(newImages);
        }
        existingProduct.updateDetails(dto, category);
        transProductRepository.save(existingProduct);
    }

    // 4. 교환 상품 수정
    @Transactional
    public void updateTradeProduct(Long productId, TradeProductUpdateDto dto, List<MultipartFile> files) throws IOException {
        // 기존 교환 상품 찾기
        TradeProduct existingProduct = tradeProductRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // 카테고리 조회
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));

        // 희망 카테고리 조회
        List<Category> wishCategories = categoryRepository.findAllById(dto.getWishCategoryIds());

        // 이미지 업로드 및 저장
        if (files != null && !files.isEmpty()) {
            List<String> uploadedUrls = s3Uploader.upload(files, "trade-product-images");
            dto.setImageUrls(uploadedUrls);

            // 기존 이미지 삭제 및 새 이미지 저장
            tradePImgRepository.deleteByTradeProduct(existingProduct);
            List<TradePImg> newImages = dto.toImageEntities(existingProduct);
            tradePImgRepository.saveAll(newImages);
        }

        // 희망 카테고리 업데이트
        List<WishCategory> wishCategoryEntities = dto.toWishCategoryEntities(existingProduct, wishCategories);
        wishCategoryRepository.deleteByTradeProduct(existingProduct); // 기존 희망 카테고리 삭제
        wishCategoryRepository.saveAll(wishCategoryEntities); // 새로운 희망 카테고리 저장

        // 교환 상품 상세 정보 업데이트
        existingProduct.updateDetail(dto, category);

        // 교환 상품 저장
        tradeProductRepository.save(existingProduct);
    }

    // 5. 거래 상품 삭제
    @Transactional
    public void deleteTransProduct(Long productId){
        TransProduct existingProduct = transProductRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        transProductRepository.delete(existingProduct);
    }
    // 6. 교환 상품 삭제
    @Transactional
    public void deleteTradeProduct(Long productId) {
        TradeProduct existingProduct = tradeProductRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException(ErrorStatus.PRODUCT_NOT_FOUND.getMessage()));
        tradeProductRepository.delete(existingProduct);
    }

    // 7. 거래상품 상세 조회
    @Transactional
    public TransProductResponseDto findTransProductById(Long id) {
        // 거래 상품 조회
        TransProduct selectedProduct = transProductRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ErrorStatus.PRODUCT_NOT_FOUND.getMessage()));
        List<TransRegion> transRegions = transRegionRepository.findByTransProduct(selectedProduct);
        // 상품에 연결된 유저 정보 조회
        User user = transProductRepository.findUserById(id);
        // DTO 생성 및 반환
        return new TransProductResponseDto(selectedProduct, transRegions, user);
    }

    // 8. 교환상품 상세 조회
    @Transactional
    public TradeProductResponseDto findTradeProductById(Long id) {
        // 교환 상품 조회
        TradeProduct selectedProduct = tradeProductRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ErrorStatus.PRODUCT_NOT_FOUND.getMessage()));

        // 연결된 지역 정보 조회
        List<TradeRegion> regions = tradeRegionRepository.findByTradeProduct(selectedProduct);

        // 상품에 연결된 유저 정보 조회
        User user = selectedProduct.getUser();

        // DTO 생성 및 반환
        return new TradeProductResponseDto(selectedProduct, regions, user);
    }



    // 9. 거래상품 검색 및 필터링
    public List<TransResponseListDto> getFilteredResults(
            ProductQueryHelper.Condition condition,
            Long category,
            String keyword,
            int page,
            int size
    ) {
        QTransProduct trans = QTransProduct.transProduct;

        // 필터링 조건 생성
        BooleanBuilder filterBuilder = ProductQueryHelper.createFilterBuilder(condition, category, keyword, trans);

        // 정렬 조건 생성
        OrderSpecifier<?> orderSpecifier = ProductQueryHelper.getOrderSpecifier(condition, trans);

        // 쿼리 실행 및 결과 반환
        return queryFactory.select(
                        Projections.constructor(
                                TransResponseListDto.class,
                                trans.id,
                                trans.name,
                                trans.point
                        ))
                .from(trans)
                .where(filterBuilder)
                .orderBy(orderSpecifier)
                .offset((long) page * size)
                .limit(size)
                .fetch();
    }


    // 10. 교환상품 검색 및 필터링
    public List<TradeResponseListDto> getFilteredTradeResults(
            TradeQueryHelper.Condition condition,
            Long category,
            String keyword,
            Long wishCategory,
            TradeType tradeType,
            int page,
            int size
    ) {
        QTradeProduct trade = QTradeProduct.tradeProduct;

        // 필터링 조건 생성
        BooleanBuilder filterBuilder = TradeQueryHelper.createFilterBuilder(
                condition, category, keyword, wishCategory, tradeType, trade);

        // 정렬 조건 생성
        OrderSpecifier<?> orderSpecifier = TradeQueryHelper.getOrderSpecifier(trade);

        // 쿼리 실행 및 결과 반환
        return queryFactory.select(
                        Projections.constructor(
                                TradeResponseListDto.class,
                                trade.id,
                                trade.name
                        ))
                .from(trade)
                .where(filterBuilder)
                .orderBy(orderSpecifier)
                .offset((long) page * size)
                .limit(size)
                .fetch();
    }


    // 11. 거래 상품 좋아요 취소
    @Transactional
    public boolean increaseLikeTransaction(Long productId, User user) {
        // 상품 가져오기
        TransProduct product = transProductRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException(ErrorStatus.PRODUCT_NOT_FOUND.getMessage()));

        // 좋아요 여부 확인
        boolean isLiked = transLikesRepository.existsByUserAndTransProduct(user, product);
        QTransProduct qproduct = QTransProduct.transProduct;

        if (!isLiked) {
            // 좋아요 추가
            transLikesRepository.save(new TransLikes(user, product));
            queryFactory.update(qproduct)
                    .set(qproduct.likedCount, qproduct.likedCount.add(1))
                    .where(qproduct.id.eq(productId))
                    .execute();
            return true; // 좋아요 추가됨
        } else {
            // 좋아요 취소
            TransLikes like = (TransLikes) transLikesRepository.findByUserAndTransProduct(user, product)
                    .orElseThrow(() -> new RuntimeException("좋아요 데이터가 존재하지 않습니다."));
            transLikesRepository.delete(like);
            queryFactory.update(qproduct)
                    .set(qproduct.likedCount, qproduct.likedCount.subtract(1))
                    .where(qproduct.id.eq(productId).and(qproduct.likedCount.gt(0))) // 음수 방지
                    .execute();
            return false; // 좋아요 취소됨
        }
    }

    // 12. 교환 상품 좋아요 취소
    @Transactional
    public  boolean increaseLikeTrade(Long productId, User user){
        // 중복 좋아요 방지, 실행 시 이미 좋아요를 눌렀다면 좋아요 취소
        TradeProduct tradeProduct = tradeProductRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException(ErrorStatus.PRODUCT_NOT_FOUND.getMessage()));
        boolean isLiked = tradeLikesRepository.existsByUserAndTradeProduct(user, tradeProduct);
        QTradeProduct qproduct = QTradeProduct.tradeProduct;
        if (!(isLiked)){
            tradeLikesRepository.save(new TradeLikes(user,tradeProduct));
            queryFactory.update(qproduct)
                    .set(qproduct.likedCount,qproduct.likedCount.add(1))
                    .where(qproduct.id.eq(productId))
                    .execute();
            return true;
        } else{
            TradeLikes like = (TradeLikes) tradeLikesRepository.findByUserAndTradeProduct(user,tradeProduct)
                    .orElseThrow(() -> new RuntimeException("좋아요 데이터가 존재하지 않습니다."));
            queryFactory.update(qproduct)
                    .set(qproduct.likedCount,qproduct.likedCount.subtract(1))
                    .where(qproduct.id.eq(productId).and(qproduct.likedCount.gt(0))) // 음수 방지
                    .execute();
            return false;
        }
    }




}

