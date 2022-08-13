package com.vrr.domain.converter.auth;

import com.vrr.common.code.auth.RoleType;
import com.vrr.domain.converter.CodeConverter;

import javax.persistence.Converter;

public class RoleTypeConverter extends CodeConverter<RoleType> {

    public RoleTypeConverter() {
        super(RoleType.class);
    }
}
