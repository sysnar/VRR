package com.vrr.code.auth;

import com.vrr.code.CodeConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class ProviderTypeConverter extends CodeConverter<ProviderType> {

    public ProviderTypeConverter() {
        super(ProviderType.class);
    }
}
