package com.ssafy.project.common.db.dto.social;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    private Long id;

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super((Map<String, Object>) attributes.get("kakao_account"));
        this.id = (long) attributes.get("id");
    }

    @Override
    public String getId() {
        return this.id.toString();
    }

    @Override
    public String getName() {
        return String.valueOf(((Map<String, Object>) attributes.get("profile")).get("nickname"));
    }

    @Override
    public String getEmail() {
        return String.valueOf(attributes.get("email"));
    }

    @Override
    public String getImageUrl() {
        return String.valueOf(((Map<String, Object>) attributes.get("profile")).get("thumbnail_image_url"));
    }

}