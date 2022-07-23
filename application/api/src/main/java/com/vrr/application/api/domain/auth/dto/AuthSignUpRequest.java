package com.vrr.application.api.domain.auth.dto;

import com.vrr.common.code.auth.ProviderType;
import com.vrr.common.code.auth.RoleType;
import com.vrr.domain.entity.auth.User;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthSignUpRequest {

    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "이메일 형식이 올바르지 않습니다")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(message = "비밀번호 형식이 올바르지 않습니다",
            regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,50}")
    private String password;

    @NotBlank(message = "유저명을 입력해주세요")
    @Size(min = 3, max = 8, message = "유저명은 3자 이상 8자 이하여야 합니다")
    private String username;

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .serialNumber(email)
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .emailVerified("Y")
                .profileImageUrl("")
                .providerType(ProviderType.VRR)
                .roleType(RoleType.USER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
