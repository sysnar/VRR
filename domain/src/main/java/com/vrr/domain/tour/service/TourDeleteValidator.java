package com.vrr.domain.tour.service;

import com.vrr.common.annotation.DomainService;
import com.vrr.domain.auth.domain.User;
import com.vrr.domain.auth.domain.UserRepository;
import com.vrr.domain.tour.domain.Tour;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class TourDeleteValidator {

    private final UserRepository userRepository;

    public void validate(Tour tour, String serial) {
        validate(tour, getUser(serial));
    }

    private void validate(Tour tour, User user) {
        if (!tour.isLeader(user)) {
            throw new IllegalArgumentException("Tour can only deleted by leader");
        }

        if (tour.getDeletedAt() != null) {
            throw new IllegalStateException("Tour has already deleted");
        }
    }

    private User getUser(String serial) {
        return userRepository.findBySerial(serial).orElseThrow(IllegalArgumentException::new);
    }
}
