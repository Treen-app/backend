package com.app.treen.auth.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import software.amazon.awssdk.annotations.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {

    @NotNull
    @Size(min = 5, max = 12)
    private String id; // 사용자 ID

    @NotNull
    @Size(min = 8, max = 15)
    private String password; // 사용자 비밀번호
}
