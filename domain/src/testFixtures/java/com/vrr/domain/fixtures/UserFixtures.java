package com.vrr.domain.fixtures;

import com.vrr.common.code.auth.ProviderType;
import com.vrr.common.code.auth.RoleType;
import com.vrr.domain.auth.domain.User;
import com.vrr.domain.auth.domain.User.UserBuilder;

import java.time.LocalDateTime;

public class UserFixtures {

    public static UserBuilder aUser() {
        return User.builder()
                .serialNumber("22d4a8bc-9478-46b4-80cf-09e2bbbfb5a4")
                .email("sysnar@gmail.com")
                .emailVerified("Y")
                .password("{bcrypt}as18j12389AS!132")
                .username("sysnar")
                .profileImageUrl("https://avatars.githubusercontent.com/u/85546022?v=4")
                .providerType(ProviderType.VRR)
                .roleType(RoleType.USER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now());
    }
}
