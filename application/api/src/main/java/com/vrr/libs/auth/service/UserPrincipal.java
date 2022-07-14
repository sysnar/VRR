package com.vrr.libs.auth.service;

import com.vrr.code.auth.ProviderType;
import com.vrr.code.auth.RoleType;
import com.vrr.entity.auth.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

@Getter
@Builder
public class UserPrincipal implements UserDetails, OAuth2User {

    private final String id;
    private final String username;
    private final String email;
    private final String password;
    private final ProviderType providerType;
    private final RoleType roleType;
    private final String emailVerified;
    private final Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;

    @Override
    public String getName() {
        return id;
    }

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    public <A> A getAttribute(String name) {
        return OAuth2User.super.getAttribute(name);
    }

    public static UserPrincipal create(final User user) {
        return UserPrincipal.builder()
                .id(user.getSerialNumber())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .emailVerified(user.getEmailVerified())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(RoleType.USER.getCode())))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
    }

    public static UserPrincipal create(User user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = create(user);
        userPrincipal.setAttributes(attributes);

        return userPrincipal;
    }

    private void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
}
