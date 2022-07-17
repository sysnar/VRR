package com.vrr.common.code;

public interface CodeType {

    /**
     * 시스템에서 사용하는 공통코드를 반환합니다.
     * @return String 공통코드값
     */
    String getCode();

    /**
     * 사용자에게 보여지는 공통코드 라벨을 반환합니다.
     * @return String 공통코드 라벨명
     */
    String getLabel();
}