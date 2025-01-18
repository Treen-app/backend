package com.app.treen.products.controller;

import com.app.treen.common.response.code.status.SuccessStatus;
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
import com.app.treen.products.entity.enumeration.TradeType;
import com.app.treen.products.service.ProductService;
import com.app.treen.user.entity.User;
import com.app.treen.user.service.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "거래 상품 등록", description = "거래 상품을 등록합니다.")
    @PostMapping(value = "/transaction/save", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<TransProductResponseDto> saveTransProduct(
            @RequestPart("images") List<MultipartFile> images, // 이미지 리스트로 변경
            @RequestPart("product") TransProductSaveDto requestDto,
            User user
    ) throws IOException {
      //  User user = new User(); // 현재 사용자의 정보를 가져오는 로직 추가 필요
        TransProductResponseDto responseDto = productService.saveTransProduct(requestDto,images, user);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Operation(summary = "교환 상품 등록", description = "거래 상품을 등록합니다.")
    @PostMapping(value = "/trade/save", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<TradeProductResponseDto> saveTradeProduct(
            @RequestPart("images") List<MultipartFile> images,
            @RequestPart("product") TradeProductSaveDto requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) throws IOException {
        TradeProductResponseDto responseDto = productService.saveTradeProduct(requestDto,images, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Operation(summary = "거래 상품 수정",description = "거래 상품을 수정합니다.")
    @PutMapping(value = "/transaction/edit/{productId}",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> editTransProduct(
            @PathVariable Long productId,
            @RequestPart("images") List<MultipartFile> images,
            @RequestPart("product") TransProductUpdateDto requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails) throws IOException {
        productService.updateTransProduct(productId,requestDto,images);
        return ResponseEntity.ok(SuccessStatus.PRODUCT_EDIT_SUCCESS);
    }

    @Operation(summary = "교환 상품 수정",description = "교환 상품을 수정합니다.")
    @PutMapping(value = "/trade/edit/{productId}",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> editTradeProduct(
            @PathVariable Long productId,
            @RequestPart("images") List<MultipartFile> images,
            @RequestPart("product") TradeProductUpdateDto requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails) throws IOException {
        productService.updateTradeProduct(productId,requestDto,images);
        return ResponseEntity.ok(SuccessStatus.PRODUCT_EDIT_SUCCESS);
    }

    @Operation(summary = "거래 상품 삭제", description = "거래 상품을 삭제합니다.")
    @DeleteMapping(value = "/transaction/delete/{productId}")
    public ResponseEntity<?> deleteTransProduct(@PathVariable Long productId){
        productService.deleteTransProduct(productId);
        return ResponseEntity.ok(SuccessStatus.PRODUCT_DELETE_SUCCESS);
    }

    @Operation(summary = "교환 상품 삭제", description = "교환 상품을 삭제합니다.")
    @DeleteMapping(value = "/trade/delete/{productId}")
    public ResponseEntity<?> deleteTradeProduct(@PathVariable Long productId){
        productService.deleteTradeProduct(productId);
        return ResponseEntity.ok(SuccessStatus.PRODUCT_DELETE_SUCCESS);
    }

    @Operation(summary = "거래 상품 상세 조회", description = "거래 상품을 상세 조회합니다.")
    @GetMapping(value = "/transaction/view/{productId}")
    public ResponseEntity<?> viewTransProductById(@PathVariable Long productId) {
        TransProductResponseDto transProductResponseDto = productService.findTransProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(transProductResponseDto);
    }

    @Operation(summary = "교환 상품 상세 조회", description = "교환 상품을 상세 조회합니다.")
    @GetMapping(value = "/trade/view/{productId}")
    public ResponseEntity<?> viewTradeProductById(@PathVariable Long productId){
        TradeProductResponseDto tradeProductResponseDto = productService.findTradeProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(tradeProductResponseDto);
    }

    @Operation(summary = "거래상품 검색 및 필터링", description = "거래 상품을 검색 및 필터링합니다.")
    @GetMapping("/transaction/search")
    public ResponseEntity<List<TransResponseListDto>> getFilteredResults(
            @RequestParam(required = false) ProductQueryHelper.Condition condition, // 거래 조건 (최신순, 인기순 등)
            @RequestParam(required = false) Long category,                        // 카테고리
            @RequestParam(required = false) String keyword,                       // 검색어
            @RequestParam(defaultValue = "0") int page,                           // 페이지 번호
            @RequestParam(defaultValue = "10") int size                           // 페이지 크기
    ) {
        List<TransResponseListDto> listDto = productService.getFilteredResults(condition, category, keyword, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(listDto);
    }



    @Operation(summary = "교환상품 검색 및 필터링", description = "교환 상품을 검색 및 필터링합니다.")
    @GetMapping("/trade/search")
    public ResponseEntity<List<TradeResponseListDto>> getFilteredTradeResults(
            @RequestParam(required = false) TradeQueryHelper.Condition condition,   // 거래 조건 (최신순, 인기순 등)
            @RequestParam(required = false) Long category,                         // 카테고리
            @RequestParam(required = false) String keyword,                        // 검색어
            @RequestParam(required = false) Long wishCategory,                     // 희망 카테고리
            @RequestParam(required = false) TradeType tradeType,                   // 거래 타입 (교환, 판매)
            @RequestParam(defaultValue = "0") int page,                            // 페이지 번호
            @RequestParam(defaultValue = "10") int size                            // 페이지 크기
    ) {
        List<TradeResponseListDto> listDto = productService.getFilteredTradeResults(condition, category, keyword, wishCategory, tradeType, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(listDto);
    }

    @Operation(summary = "거래상품 좋아요 및 취소", description = "좋아요 되어있을 경우 취소, 안되어있을 경우 좋아요 등록됩니다.")
    @GetMapping("/transaction/like/{productId}")
    public ResponseEntity<?> registerLikesTransProduct(@PathVariable Long productId, User user){
        boolean isLike = productService.increaseLikeTransaction(productId, user);
        return isLike ? ResponseEntity.status(HttpStatus.OK).body(SuccessStatus.PIN_LIKE) : ResponseEntity.status(HttpStatus.OK).body(SuccessStatus.PIN_UNLIKE);
    }

    @Operation(summary = "교환상품 좋아요 및 취소", description = "좋아요 되어있을 경우 취소, 안되어있을 경우 좋아요 등록됩니다.")
    @GetMapping("/trade/like/{productId}")
    public ResponseEntity<?> registerLikesTradeProduct(@PathVariable Long productId, User user){
        boolean isLike = productService.increaseLikeTrade(productId, user);
        return isLike ? ResponseEntity.status(HttpStatus.OK).body(SuccessStatus.PIN_LIKE) : ResponseEntity.status(HttpStatus.OK).body(SuccessStatus.PIN_UNLIKE);
    }


}
