package com.vrr.domain.converter;

import com.vrr.common.code.auth.RoleType;
import com.vrr.domain.converter.auth.CodeConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class RoleTypeConverter extends CodeConverter<RoleType> {

    public RoleTypeConverter() {
        super(RoleType.class);
    }
}
