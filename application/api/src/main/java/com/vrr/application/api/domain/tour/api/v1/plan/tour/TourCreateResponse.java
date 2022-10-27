package com.vrr.application.api.domain.tour.api.v1.plan.tour;

import com.vrr.domain.tour.domain.Tour;
import lombok.Getter;

@Getter
public class TourCreateResponse {

    private final Long tourId;

    public TourCreateResponse(Long tourId) {
        this.tourId = tourId;
    }

    public static TourCreateResponse of(Tour tour) {
        return new TourCreateResponse(tour.getId());
    }
}
