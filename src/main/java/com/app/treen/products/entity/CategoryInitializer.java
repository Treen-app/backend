package com.app.treen.products.entity;

import com.app.treen.jpa.repository.products.CategoryRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class CategoryInitializer {

    @Bean
    public ApplicationRunner initCategories(CategoryRepository categoryRepository) {
        return args -> {
            if (categoryRepository.count() == 0) {  // 중복 방지
                List<String> categories = List.of(
                        "아우터", "상의", "하의", "원피스",
                        "신발", "잡화", "키즈"
                );
                categories.forEach(name -> categoryRepository.save(new Category(null, name)));
            }
        };
    }
}
