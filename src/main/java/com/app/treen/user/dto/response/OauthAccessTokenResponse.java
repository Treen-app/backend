package com.app.treen.user.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OauthAccessTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;
}
