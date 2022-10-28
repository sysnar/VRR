package com.vrr.domain.tour.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TourMemberRepository extends JpaRepository<TourMember, Long> {

    Optional<TourMember> findByUserId(Long userId);
}
