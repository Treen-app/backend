package com.app.treen.products.service;

import com.app.treen.common.config.S3Uploader;
import com.app.treen.common.response.code.status.ErrorStatus;
import com.app.treen.common.response.exception.CustomException;
import com.app.treen.jpa.repository.products.*;
import com.app.treen.products.dto.ProductQueryHelper;
import com.app.treen.products.dto.TradeQueryHelper;
import com.app.treen.products.dto.request.*;
import com.app.treen.products.dto.response.TradeProductResponseDto;
import com.app.treen.products.dto.response.TradeResponseListDto;
import com.app.treen.products.dto.response.TransProductResponseDto;
import com.app.treen.products.dto.response.TransResponseListDto;
import com.app.treen.products.entity.*;
import com.app.treen.products.entity.enumeration.TradeType;
import com.app.treen.user.entity.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
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
import java.util.stream.Collectors;

import static com.app.treen.products.entity.QTradePImg.tradePImg;

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

    private final JPAQueryFactory queryFactory;

    // 1 . 상품 거래 등록
    @Transactional
    public TransProductResponseDto saveTransProduct(TransactionRequestDto dto, List<MultipartFile> files, User user) throws IOException {
        TransProductSaveDto transProductDto = dto.getProductRequest();
        TransImgRequestDto transImgRequestDto = dto.getImageRequest();
        TransRegionRequestDto regionRequestDto = dto.getRegionRequest();

        // Category 조회
        Category category = categoryRepository.findById(transProductDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));

        // TransProduct 저장
        TransProduct transProduct = transProductRepository.save(transProductDto.toEntity(user, category));
        tradeProductRepository.flush();
        if (transProduct.getId() == null) {
            throw new IllegalStateException("TradeProduct 저장 후 ID가 생성되지 않았습니다.");
        }

        // S3에 파일 업로드 개수 검증
        if (files == null || files.isEmpty()) {
            throw new CustomException(ErrorStatus.IMAGE_MUST_BE_UPLOADED);
        } else if (files.size() > 5) {
            throw new CustomException(ErrorStatus.IMAGE_OVER_UPLOADED);
        }

        List<String> uploadedUrls = s3Uploader.upload(files, "trans-product-images");

        // 이미지 저장
        List<TransPImg> images = transImgRequestDto.toImageEntities(transProduct,uploadedUrls);
        transPImgRepository.saveAll(images);

        // Region 리스트 조회
        List<Region> regions = regionRepository.findAllById(regionRequestDto.getRegionIds());

        // TransRegion 저장 (regions가 비어있지 않은 경우에만 저장)
        List<TransRegion> transRegions = (regions.isEmpty()) ? null : regionRequestDto.toRegionEntities(transProduct, regions);
        if (transRegions != null) {
            transRegionRepository.saveAll(transRegions);
        }

        return new TransProductResponseDto(transProduct, transRegions, images,user);

    }


    // 2. 상품 교환 등록
    @Transactional
    public TradeProductResponseDto saveTradeProduct(TradeRequestDto dto, List<MultipartFile> files, User user) throws IOException {
        TradeProductSaveDto tradeProductDto = dto.getTradeProduct();
        ImgRequestDto tradeImgRequestDto = dto.getImgRequest();
        RegionRequestDto regionRequestDto = dto.getRegionRequest();
        WishCategoryRequestDto wishCategoryRequestDto = dto.getWishCategoryRequest();

        // Category 조회
        Category category = categoryRepository.findById(tradeProductDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));

        // TradeProduct 저장
        TradeProduct tradeProduct = tradeProductRepository.save(tradeProductDto.toEntity(user, category));
        tradeProductRepository.flush();

        if (tradeProduct.getId() == null) {
            throw new IllegalStateException("TradeProduct 저장 후 ID가 생성되지 않았습니다.");
        }

        // S3 파일 업로드 개수 검증 (검증 후 업로드 진행)
        if (files == null || files.isEmpty()) {
            throw new CustomException(ErrorStatus.IMAGE_MUST_BE_UPLOADED);
        } else if (files.size() > 5) {
            throw new CustomException(ErrorStatus.IMAGE_OVER_UPLOADED);
        }

        // S3에 파일 업로드 및 URL 변환
        List<String> uploadedUrls = s3Uploader.upload(files, "trade-product-images");
        tradeImgRequestDto.toImageEntities(tradeProduct,uploadedUrls);

        // 이미지 저장
        List<TradePImg> images = tradeImgRequestDto.toImageEntities(tradeProduct, uploadedUrls);
        tradePImgRepository.saveAll(images);
        List<Long> wishCategoryIds = wishCategoryRequestDto.getWishCategoryIds();

        List<WishCategory> wishCategoryEntities = new ArrayList<>();
        // 교환 타입에 따라 설정
        if (tradeProductDto.getTradeType() == TradeType.ANYTHING){
            if (wishCategoryIds != null && !wishCategoryIds.isEmpty()) {
                List<Category> wishCategories = categoryRepository.findAllById(wishCategoryIds);
                wishCategoryEntities = wishCategoryRequestDto.toWishCategoryEntities(tradeProduct, wishCategories);
                wishCategoryRepository.saveAll(wishCategoryEntities);
            }

        }

        // 지역 정보 저장
        List<TradeRegion> tradeRegions = new ArrayList<>();
        if (regionRequestDto.getRegionIds() != null && !regionRequestDto.getRegionIds().isEmpty()) {
            List<Region> regions = regionRepository.findAllById(regionRequestDto.getRegionIds());

            // regions가 비어있지 않으면 TradeRegion 생성 후 저장
            if (!regions.isEmpty()) {
                tradeRegions = regionRequestDto.toRegionEntities(tradeProduct, regions);
                tradeRegionRepository.saveAll(tradeRegions);
            }
        }

        // 응답 DTO 생성 및 반환
        return new TradeProductResponseDto(tradeProduct, tradeRegions, images,wishCategoryEntities, user);
    }


    // 3. 거래 상품 수정
    @Transactional
    public void updateTransProduct(Long productId, TransProductUpdateDto dto)
    {
        TransProduct existingProduct = transProductRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));

        existingProduct.updateDetails(
                dto.getName(),
                dto.getUsedTerm(),
                dto.getDetail(),
                dto.getGender(),
                dto.getSize(),
                dto.getUsedRank(),
                dto.getPoint(),
                dto.getMethod(),
                category
        );

        transProductRepository.save(existingProduct);
    }

    // 4. 교환 상품 수정
    @Transactional
    public void updateTradeProduct(Long productId, TradeProductUpdateDto dto)  {
        // 기존 교환 상품 찾기
        TradeProduct existingProduct = tradeProductRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // 카테고리 조회
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));

        // 희망 카테고리 조회
        List<Category> wishCategories = categoryRepository.findAllById(dto.getWishCategoryRequest().getWishCategoryIds());


        // 희망 카테고리 업데이트
        List<WishCategory> wishCategoryEntities = dto.getWishCategoryRequest().toWishCategoryEntities(existingProduct, wishCategories);
        wishCategoryRepository.deleteByTradeProduct(existingProduct); // 기존 희망 카테고리 삭제
        wishCategoryRepository.saveAll(wishCategoryEntities); // 새로운 희망 카테고리 저장

        // 교환 상품 상세 정보 업데이트
        existingProduct.updateDetail(dto, category);

        // 교환 상품 저장
        tradeProductRepository.save(existingProduct);
    }

    // 5. 거래 상품 삭제
    @Transactional
    public void deleteTransProduct(Long productId) {
        TransProduct existingProduct = transProductRepository.findById(productId)
                .orElseThrow(() ->  new CustomException(ErrorStatus.PRODUCT_NOT_FOUND));
        List<String> imageUrls = transPImgRepository.findByTransProduct(existingProduct)
                .stream()
                .map(TransPImg::getImgUrl)
                .collect(Collectors.toList());
        imageUrls.forEach(s3Uploader::deleteImage); // S3에서 이미지 삭제
        transPImgRepository.deleteByTransProduct(existingProduct);
        transProductRepository.delete(existingProduct);
    }

    // 6. 교환 상품 삭제
    @Transactional
    public void deleteTradeProduct(Long productId) {
        TradeProduct existingProduct = tradeProductRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorStatus.PRODUCT_NOT_FOUND));
        List<String> imageUrls = tradePImgRepository.findAllByTradeProduct(existingProduct)
                        .stream()
                        .map(TradePImg::getImgUrl)
                        .collect(Collectors.toList());
        tradePImgRepository.deleteByTradeProduct(existingProduct);
        tradeProductRepository.delete(existingProduct);
    }

    // 7. 거래상품 상세 조회
    @Transactional
    public TransProductResponseDto findTransProductById(Long id) {
        // 거래 상품 조회
        TransProduct selectedProduct = transProductRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorStatus.PRODUCT_NOT_FOUND));
        List<TransRegion> transRegions = transRegionRepository.findByTransProduct(selectedProduct);
        // 이미지 조회
        List<TransPImg> transPImgs = transPImgRepository.findByTransProduct(selectedProduct);
        // 상품에 연결된 유저 정보 조회
        User user = transProductRepository.findUserById(id);
        // DTO 생성 및 반환
        return new TransProductResponseDto(selectedProduct, transRegions,transPImgs,user);
    }

    // 8. 교환상품 상세 조회
    @Transactional
    public TradeProductResponseDto findTradeProductById(Long id) {
        // 교환 상품 조회
        TradeProduct selectedProduct = tradeProductRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorStatus.PRODUCT_NOT_FOUND));

        // 연결된 지역 정보 조회
        List<TradeRegion> regions = tradeRegionRepository.findByTradeProduct(selectedProduct);

        // 희망 카테고리 조회
        List<WishCategory> wishCategories = wishCategoryRepository.findAllByTradeProduct(selectedProduct);

        // 이미지 조회
        List<TradePImg> tradePImgs = tradePImgRepository.findAllByTradeProduct(selectedProduct);

        // 상품에 연결된 유저 정보 조회
        User user = selectedProduct.getUser();

        // DTO 생성 및 반환
        return new TradeProductResponseDto(selectedProduct, regions, tradePImgs, wishCategories,user);
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

        List<TransProduct> products = queryFactory
                .selectFrom(trans)
                .where(filterBuilder)
                .orderBy(orderSpecifier)
                .offset((long) page * size)
                .limit(size)
                .fetch();

        return products.stream().map(product -> {
            TransPImg mainImage = (TransPImg) transPImgRepository
                    .findFirstByTransProductAndIsMainTrue(product)
                    .orElse(null); // 대표 이미지가 없으면 null

            return new TransResponseListDto(product, mainImage);
        }).collect(Collectors.toList());
    }



    public List<TradeResponseListDto> getFilteredTradeResults(
            TradeQueryHelper.Condition condition,
            Long category,
            String keyword,
            List<Long> wishCategoryIds,
            TradeType tradeType,
            int page,
            int size
    ) {
        QTradeProduct trade = QTradeProduct.tradeProduct;

        // 필터링 조건 생성
        BooleanBuilder filterBuilder = TradeQueryHelper.createFilterBuilder(
                condition, category, keyword, wishCategoryIds, tradeType, trade
        );

        // 정렬 조건 생성
        OrderSpecifier<?> orderSpecifier = TradeQueryHelper.getOrderSpecifier(trade);

        List<TradeProduct> products = queryFactory
                .selectFrom(trade)
                .where(filterBuilder)
                .orderBy(orderSpecifier)
                .offset((long) page * size)
                .limit(size)
                .distinct()
                .fetch();

        return products.stream().map(product -> {
            TradePImg mainImage = tradePImgRepository
                    .findByTradeProductAndIsMainTrue(product)
                    .orElse(null);
            return new TradeResponseListDto(product, mainImage);
        }).collect(Collectors.toList());
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

