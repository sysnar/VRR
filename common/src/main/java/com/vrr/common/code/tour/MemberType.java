package com.vrr.common.code.tour;

import com.vrr.common.code.CodeType;

public enum MemberType implements CodeType {

    NORMAL("투어 참여자", "MBRTP_NORMAL"),
    LEADER("투어 주최자", "MBRTP_LEADER");

    private final String label;
    private final String code;

    MemberType(String label, String code) {
        this.code = code;
        this.label = label;
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
