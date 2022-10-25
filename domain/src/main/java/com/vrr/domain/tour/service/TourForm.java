package com.vrr.domain.tour.service;

import com.vrr.common.code.tour.TourType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TourForm {

    private final String serial;
    private final TourType type;
    private final String title;
    private final String summary;
    private final String departurePoint;
    private final String arrivalPoint;
    private final LocalDateTime startAt;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
