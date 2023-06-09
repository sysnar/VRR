package com.vrr.domain.tour.service;

import com.vrr.common.annotation.DomainService;
import com.vrr.common.code.tour.MemberType;
import com.vrr.domain.auth.domain.User;
import com.vrr.domain.auth.domain.UserRepository;
import com.vrr.domain.tour.domain.Tour;
import com.vrr.domain.tour.domain.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@DomainService
@RequiredArgsConstructor
public class TourCreator {

    private final UserRepository userRepository;
    private final TourRepository tourRepository;

    @Transactional
    public Tour create(String userSerial, Tour tour, LocalDateTime now) {
        User user = userRepository.findBySerial(userSerial)
                .orElseThrow(() -> new IllegalArgumentException("None existing user"));

        tour.addMember(user, MemberType.LEADER, now);
        return tourRepository.save(tour);
    }
}
