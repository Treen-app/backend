package com.app.treen.mypage.dto;

import lombok.Data;

@Data
public class UserProfileUpdateDto {
    private String nickname;
    private String gender;
    private String birthDate;
    private String userId;
    private int height;
    private int weight;
    private int footSize;
    private String clothingSize;
}
