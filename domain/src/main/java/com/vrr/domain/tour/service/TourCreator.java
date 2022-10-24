package com.vrr.domain.tour.service;

import com.vrr.common.annotation.DomainService;
import com.vrr.domain.auth.domain.User;
import com.vrr.domain.auth.domain.UserRepository;
import com.vrr.domain.tour.domain.Tour;
import com.vrr.domain.tour.domain.TourRepository;
import lombok.RequiredArgsConstructor;


@DomainService
@RequiredArgsConstructor
public class TourCreator {

    private final UserRepository userRepository;
    private final TourRepository tourRepository;
    private final TourMapper tourMapper;

    public Tour create(TourForm tourForm) {
        User user = userRepository.findBySerialNumber(tourForm.getSerial());
        if (user == null) {
            throw new IllegalStateException("None existing user");
        }
        return tourRepository.save(tourMapper.mapFrom(user.getId(), tourForm));
    }
}
