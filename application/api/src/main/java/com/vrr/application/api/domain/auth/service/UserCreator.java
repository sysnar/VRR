package com.vrr.application.api.domain.auth.service;

import com.vrr.application.api.domain.auth.dto.AuthSignUpRequest;
import com.vrr.application.api.domain.auth.repository.UserQueryRepository;
import com.vrr.common.annotation.ApplicationService;
import com.vrr.domain.entity.auth.User;
import com.vrr.domain.entity.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@ApplicationService
@RequiredArgsConstructor
public class UserCreator {

    private final UserQueryRepository userQueryRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User create(AuthSignUpRequest signUpRequest) {
        validateDuplicateEmail(signUpRequest);
        User user = signUpRequest.toUser(passwordEncoder);
        return userRepository.save(user);
    }

    private void validateDuplicateEmail(AuthSignUpRequest signUpRequest) {
        Optional<User> userOptional = userQueryRepository.findByEmail(signUpRequest.getEmail());
        if (userOptional.isPresent()) {
            throw new IllegalArgumentException("해당 이메일을 사용중인 사용자가 존재합니다.");
        }
    }
}
