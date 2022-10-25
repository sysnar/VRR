package com.vrr.domain.tour.service;

import com.vrr.common.code.tour.MemberType;
import com.vrr.domain.tour.domain.Tour;
import com.vrr.domain.tour.domain.TourGroup;
import com.vrr.domain.tour.domain.TourMember;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TourMapper {

    public Tour mapFrom(Long userId, TourForm tourForm) {
        Tour tour = toTour(tourForm);
        tour.addMember(toTourMember(userId, tour));
        return tour;
    }

    private Tour toTour(TourForm tourForm) {
        return Tour.builder()
                .title(tourForm.getTitle())
                .summary(tourForm.getSummary())
                .type(tourForm.getType())
                .departurePoint(tourForm.getDeparturePoint())
                .arrivalPoint(tourForm.getArrivalPoint())
                .startAt(tourForm.getStartAt())
                .createdAt(tourForm.getCreatedAt())
                .updatedAt(tourForm.getUpdatedAt())
                .open(true)
                .tourGroup(new TourGroup())
                .build();
    }

    private TourMember toTourMember(Long userId, Tour tour) {
        return TourMember.builder()
                .userId(userId)
                .tour(tour)
                .type(MemberType.LEADER)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
