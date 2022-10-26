package com.vrr.domain.auth.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String serialNumber);

    User findBySerialNumber(String uuid);

    @Query("select u from User u where u.serialNumber = :serial")
    Optional<User> findBySerial(@Param("serial") String serial);
}
