package com.vrr.application.api.domain.auth.repository;

import com.vrr.domain.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserQueryRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String username);
}
