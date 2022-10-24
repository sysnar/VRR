package com.vrr.application.api.domain.tour.api.v1.tour;

import com.vrr.domain.tour.domain.Tour;
import lombok.Getter;

@Getter
public class TourCreateResponse {

    private Long tourId;

    public TourCreateResponse(Tour tour) {
        this.tourId = tour.getId();
    }
}
