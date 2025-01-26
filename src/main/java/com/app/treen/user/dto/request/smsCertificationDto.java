package com.app.treen.user.dto.request;


import lombok.Getter;

public class smsCertificationDto {
    @Getter
    public static class SmsCertificationRequest {
        private String phone;
        private String certificationNumber;
    }
}
