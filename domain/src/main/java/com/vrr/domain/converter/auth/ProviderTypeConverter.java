package com.vrr.domain.converter.auth;

import com.vrr.common.code.auth.ProviderType;
import com.vrr.domain.converter.CodeConverter;

import javax.persistence.Converter;

public class ProviderTypeConverter extends CodeConverter<ProviderType> {

    public ProviderTypeConverter() {
        super(ProviderType.class);
    }
}
