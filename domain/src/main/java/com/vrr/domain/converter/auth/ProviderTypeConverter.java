package com.vrr.domain.converter.auth;

import com.vrr.common.code.auth.ProviderType;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class ProviderTypeConverter extends CodeConverter<ProviderType> {

    public ProviderTypeConverter() {
        super(ProviderType.class);
    }
}
