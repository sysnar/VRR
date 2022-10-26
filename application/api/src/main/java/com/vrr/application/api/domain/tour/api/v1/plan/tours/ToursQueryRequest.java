package com.vrr.application.api.domain.tour.api.v1.plan.tours;

import com.vrr.common.code.tour.TourType;
import lombok.Getter;

@Getter
public class ToursQueryRequest {

    private final String cursor;
    private final TourType type;

    public ToursQueryRequest(String cursor, TourType type) {
        this.cursor = cursor;
        this.type = type;
    }
}
