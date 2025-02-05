package com.app.treen.mypage.dto;

import lombok.Getter;

@Getter
public class UpdateUserProfileDto {
    private String nickname;
    private String gender;
    private String birthDate;
    private Integer height;
    private Integer weight;
    private Integer footSize;
    private String clothingSize;
    private String phoneNumber;
}
