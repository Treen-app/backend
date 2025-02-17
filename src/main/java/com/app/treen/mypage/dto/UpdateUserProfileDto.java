package com.app.treen.mypage.dto;

import com.app.treen.products.entity.enumeration.Gender;
import com.app.treen.products.entity.enumeration.Size;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateUserProfileDto {

    @NotNull
    private String nickname;

    @NotNull
    private Gender gender;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private int height;

    @NotNull
    private int weight;

    @NotNull
    private Size size;

    private MultipartFile profileImg;
}