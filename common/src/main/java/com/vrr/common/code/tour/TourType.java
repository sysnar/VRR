package com.vrr.common.code.tour;

import com.vrr.common.code.CodeType;

public enum TourType implements CodeType {

    SLOW("저속", "TURTP_SLOW"),
    FAST("고속", "TURTP_FAST"),
    SUPER_FAST("초고속", "TURTP_SUPER_FAST");

    private final String label;
    private final String code;

    TourType(String label, String code) {
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
