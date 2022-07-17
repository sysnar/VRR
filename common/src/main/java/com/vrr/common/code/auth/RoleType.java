package com.vrr.common.code.auth;

import com.vrr.common.code.CodeType;

public enum RoleType implements CodeType {

    USER("일반 사용자 권한", "ROLTP_USER"),
    ADMIN("관리자 권한", "ROLTP_ADMIN"),
    GUEST("게스트 권한", "ROLTP_GUEST");

    private final String label;
    private final String code;

    RoleType(String label, String code) {
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
