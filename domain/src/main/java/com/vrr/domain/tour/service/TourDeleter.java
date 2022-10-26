package com.vrr.domain.tour.service;

import com.vrr.common.annotation.DomainService;
import com.vrr.domain.tour.domain.Tour;
import com.vrr.domain.tour.domain.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@DomainService
@RequiredArgsConstructor
public class TourDeleter {

    private final TourRepository tourRepository;
    private final TourDeleteValidator tourDeleteValidator;

    @Transactional
    public void delete(String serial, Long tourId, LocalDateTime deletedAt) {
        Tour tour = tourRepository.findById(tourId).orElseThrow(IllegalArgumentException::new);
        tour.delete(serial, deletedAt, tourDeleteValidator);
        tourRepository.save(tour);
    }
}
