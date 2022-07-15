package com.vrr.code.auth;

import com.vrr.code.CodeType;

public enum ProviderType implements CodeType {

    GOOGLE("구글", "PVDTP_GOOGLE"),
    KAKAO("카카오", "PVDTP_KAKAO"),
    NAVER("네이버", "PVDTP_NAVER");

    private final String label;
    private final String code;

    ProviderType(String label, String code) {
        this.label = label;
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
