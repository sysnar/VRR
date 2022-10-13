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
        TourGroup tourGroup = toTourGroup(userId);
        return Tour.builder()
                .title(tourForm.getTitle())
                .summary(tourForm.getSummary())
                .type(tourForm.getType())
                .departure(tourForm.getDeparture())
                .arrival(tourForm.getArrival())
                .startAt(tourForm.getStartAt())
                .tourGroup(tourGroup)
                .build();
    }

    private TourGroup toTourGroup(Long userId) {
        return new TourGroup(
                TourMember.builder()
                        .userId(userId)
                        .type(MemberType.LEADER)
                        .createdAt(LocalDateTime.now())
                        .build());
    }
}
