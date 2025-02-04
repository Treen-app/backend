package com.app.treen.user.entity;


import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Builder(access = AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2Attribute {
    private Map<String,Object> attributes;
    private String attributeKey;
    private String email;
    private String picture;

    public static OAuth2Attribute of(String provider, String attributeKey, Map<String, Object> attributes) {
        switch (provider) {
            case "google":
                return ofGoogle(attributeKey,attributes);
            case "kakao":
                return ofKakao("email", attributes);
            case "naver":
                return ofNaver("id",attributes);
            default:
                throw new RuntimeException();

        }
    }

    private static OAuth2Attribute ofGoogle(String attributeKey, Map<String,Object> attributes) {
        return OAuth2Attribute.builder()
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributeKey(attributeKey)
                .attributes(attributes)
                .build();
    }

    private static OAuth2Attribute ofNaver(String attributeKey, Map<String,Object> attributes){
        Map<String,Object> response = (Map<String, Object>) attributes.get("response");
        return OAuth2Attribute.builder()
                .email((String) response.get("email"))
                .picture((String)response.get("profile_image"))
                .attributeKey(attributeKey)
                .attributes(response)
                .build();
    }

    private static OAuth2Attribute ofKakao(String attributeKey, Map<String,Object> attributes) {
        Map<String,Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String,Object> kakaoProfile = (Map<String, Object>) attributes.get("profile");
        return OAuth2Attribute.builder()
                .email((String) kakaoAccount.get("email"))
                .picture((String) kakaoProfile.get("profile_image"))
                .attributes(attributes)
                .attributeKey(attributeKey)
                .build();
      }

      public Map<String,Object> convertToMap() {
        Map<String,Object> map = new HashMap<>();
        map.put("id",attributeKey);
        map.put("key",attributeKey);
        map.put("email",email);
        map.put("picture",picture);
        return map;
      }
}
