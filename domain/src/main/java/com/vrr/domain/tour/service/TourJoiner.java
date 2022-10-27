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
public class TourJoiner {

    private final UserRepository userRepository;
    private final TourRepository tourRepository;

    @Transactional
    public void join(Long tourId, String userSerial, LocalDateTime now) {
        Tour tour = tourRepository.findById(tourId).orElseThrow(IllegalArgumentException::new);
        User user = userRepository.findBySerial(userSerial).orElseThrow(IllegalArgumentException::new);
        tour.addMember(user, MemberType.NORMAL, now);
        tourRepository.save(tour);
    }
}
