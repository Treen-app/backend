package com.app.treen.products;

import com.app.treen.products.dto.request.TradeProductSaveDto;
import com.app.treen.products.dto.response.TradeProductResponseDto;
import com.app.treen.products.entity.Category;
import com.app.treen.products.entity.TradeProduct;
import com.app.treen.products.entity.repository.CategoryRepository;
import com.app.treen.products.entity.repository.TradeProductRepository;
import com.app.treen.products.service.ProductService;
import com.app.treen.common.config.S3Uploader;
import com.app.treen.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductControllerTest {

    @Mock
    private TradeProductRepository tradeProductRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private S3Uploader s3Uploader;

    @Mock
    private User user;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private ProductService productService;

    private Category category;
    private TradeProductSaveDto tradeProductSaveDto;

    @BeforeEach
    void setUp() throws IOException {
        // 테스트에 사용할 더미 데이터 설정
        category = new Category();
        category.setId(1L);
        category.setName("Category 1");

        // User 객체를 실제로 생성하여 사용
        user = new User();
        user.setId(1L);
        user.setUserName("testUser");  // 실제 필드명을 맞춰서 수정
        user.setLoginId("testLoginId");
        user.setPassword("testPassword");
        user.setNickname("Test Nick");
        user.setPhone("010-1234-5678");
        user.setGender(0);  // 남자
        user.setBirthDate(null);
        user.setHeight(175);
        user.setWeight(70);
        user.setSize("M");
        user.setStatus("ACTIVE");
        user.setProfileImgUrl("http://example.com/profile.jpg");

        tradeProductSaveDto.setCategoryId(1L);
        tradeProductSaveDto.setName("Trade Product 1");

        // Mocking repository and external service methods
        when(categoryRepository.findById(1L)).thenReturn(java.util.Optional.of(category));
        when(s3Uploader.upload(anyList(), eq("trade-product-images"))).thenReturn(Arrays.asList("url1", "url2"));
    }

    @Test
    void saveTradeProductTest() throws IOException {
        // Given: TradeProductSaveDto에 따라 새로운 상품 저장

        // TradeProductSaveDto의 값을 설정 (setter 사용)
        TradeProductSaveDto tradeProductSaveDto = new TradeProductSaveDto();
        tradeProductSaveDto.setCategoryId(1L);
        tradeProductSaveDto.setName("Trade Product 1");

        // Mock TradeProduct에 대한 저장
        TradeProduct savedProduct = new TradeProduct();
        savedProduct.setId(1L);
        savedProduct.setName(tradeProductSaveDto.getName());
        savedProduct.setCategory(category);
        savedProduct.setUser(user);  // 생성된 User를 TradeProduct에 설정
        when(tradeProductRepository.save(any(TradeProduct.class))).thenReturn(savedProduct);

        // When: ProductService의 saveTradeProduct 메서드를 호출
        TradeProductResponseDto responseDto = productService.saveTradeProduct(tradeProductSaveDto, Arrays.asList(file), user);

        // Then: 결과 값이 예상한 값과 일치하는지 확인
        assertNotNull(responseDto);
        assertEquals(savedProduct.getName(), responseDto.getName());
        assertNotNull(responseDto.getImageUrls());
        assertEquals(2, responseDto.getImageUrls().size());
    }

    @Test
    void saveTradeProductCategoryNotFoundTest() throws IOException {
        // Given: 존재하지 않는 카테고리 ID
        when(categoryRepository.findById(999L)).thenReturn(java.util.Optional.empty());

        // When: ProductService의 saveTradeProduct 메서드를 호출
        assertThrows(IllegalArgumentException.class, () -> {
            productService.saveTradeProduct(tradeProductSaveDto, Arrays.asList(file), user);
        });
    }

}
