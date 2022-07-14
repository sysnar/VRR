package com.vrr.code.auth;

import com.vrr.code.CodeConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class RoleTypeConverter extends CodeConverter<RoleType> {

    public RoleTypeConverter() {
        super(RoleType.class);
    }
}
