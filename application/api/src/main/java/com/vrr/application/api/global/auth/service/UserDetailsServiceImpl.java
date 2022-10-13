package com.vrr.application.api.global.auth.service;

import com.vrr.domain.auth.domain.User;
import com.vrr.domain.auth.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String emailOrSerialNumber) throws UsernameNotFoundException {
        User user = userRepository.findBySerialNumber(emailOrSerialNumber);

        if (user == null) {
            user = userRepository.findByEmail(emailOrSerialNumber);
        }

        if (user == null) {
            log.warn("No record found for storedUser with emailOrId {}", emailOrSerialNumber);
            throw new UsernameNotFoundException("User with emailOrId " + emailOrSerialNumber + " not fount");
        }

        return UserPrincipal.create(user);
    }
}
