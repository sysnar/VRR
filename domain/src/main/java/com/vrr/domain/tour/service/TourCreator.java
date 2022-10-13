package com.vrr.domain.tour.service;

import com.vrr.common.annotation.DomainService;
import com.vrr.domain.auth.domain.User;
import com.vrr.domain.auth.domain.UserRepository;
import com.vrr.domain.tour.domain.TourRepository;
import lombok.RequiredArgsConstructor;


@DomainService
@RequiredArgsConstructor
public class TourCreator {

    private final UserRepository userRepository;
    private final TourRepository tourRepository;
    private final TourMapper tourMapper;

    public void create(TourForm tourForm) {
        User user = userRepository.findById(tourForm.getUserId()).orElseThrow(IllegalArgumentException::new);
        tourRepository.save(tourMapper.mapFrom(user.getId(), tourForm));
    }
}
