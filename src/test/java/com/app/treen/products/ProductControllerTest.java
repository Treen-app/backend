package com.app.treen.products;

import com.app.treen.products.controller.ProductController;
import com.app.treen.products.dto.request.TradeProductSaveDto;
import com.app.treen.products.dto.request.TransProductSaveDto;
import com.app.treen.products.dto.response.TradeProductResponseDto;
import com.app.treen.products.dto.response.TransProductResponseDto;
import com.app.treen.products.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

}

