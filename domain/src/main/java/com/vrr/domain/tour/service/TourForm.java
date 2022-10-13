package com.vrr.domain.tour.service;

import com.vrr.common.code.tour.TourType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TourForm {

    private final Long userId;
    private final TourType type;
    private final String title;
    private final String summary;
    private final String departure;
    private final String arrival;
    private final LocalDateTime startAt;
}
