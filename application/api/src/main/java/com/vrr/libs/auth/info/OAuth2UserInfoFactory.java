package com.vrr.libs.auth.info;

import com.vrr.code.auth.ProviderType;
import com.vrr.libs.auth.info.implement.GoogleOAuth2UserInfo;
import com.vrr.libs.auth.info.implement.KakaoOAuth2UserInfo;
import com.vrr.libs.auth.info.implement.NaverOAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(ProviderType providerType, Map<String, Object> attributes) {
        switch (providerType) {
            case GOOGLE: return new GoogleOAuth2UserInfo(attributes);
            case KAKAO: return new KakaoOAuth2UserInfo(attributes);
            case NAVER: return new NaverOAuth2UserInfo(attributes);
            default: throw new IllegalArgumentException("Invalid Provider Type.");
        }
    }
}
