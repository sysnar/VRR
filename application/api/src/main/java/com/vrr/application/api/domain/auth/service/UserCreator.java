package com.vrr.application.api.domain.auth.service;

import com.vrr.application.api.domain.auth.api.v1.auth.AuthSignUpRequest;
import com.vrr.common.annotation.ApplicationService;
import com.vrr.domain.auth.domain.User;
import com.vrr.domain.auth.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@ApplicationService
@RequiredArgsConstructor
public class UserCreator {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User create(AuthSignUpRequest signUpRequest) {
        validateDuplicateEmail(signUpRequest);
        User user = signUpRequest.toUser(passwordEncoder);
        return userRepository.save(user);
    }

    private void validateDuplicateEmail(AuthSignUpRequest signUpRequest) {
        User userOptional = userRepository.findByEmail(signUpRequest.getEmail());
        if (userOptional != null) {
            throw new IllegalArgumentException("해당 이메일을 사용중인 사용자가 존재합니다.");
        }
    }
}
