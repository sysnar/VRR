package com.vrr.libs.auth.service;

import com.vrr.entity.auth.User;
import com.vrr.entity.auth.UserRepository;
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
    public UserDetails loadUserByUsername(String emailOrUuid) throws UsernameNotFoundException {
        User storedUser = userRepository.findBySerialNumber(emailOrUuid)
                .orElseThrow(() -> {
                    log.warn("No record found for storedUser with emailOrId {}", emailOrUuid);
                    throw new UsernameNotFoundException("User with emailOrId " + emailOrUuid + " not fount");
                });

        return UserPrincipal.create(storedUser);
    }
}
