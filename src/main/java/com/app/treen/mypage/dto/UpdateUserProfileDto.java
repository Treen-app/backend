package com.app.treen.mypage.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserProfileDto {

    @NotNull
    private String nickname;

    @NotNull
    private String gender;

    @NotNull
    private String birthDate; // YYYY-MM-DD 형식의 String으로 전달

    @NotNull
    private int height;

    @NotNull
    private int weight;

    @NotNull
    private int footSize;

    @NotNull
    private String clothingSize;
}