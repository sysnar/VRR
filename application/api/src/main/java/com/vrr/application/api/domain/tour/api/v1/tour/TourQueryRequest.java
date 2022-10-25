package com.vrr.application.api.domain.tour.api.v1.tour;

import com.vrr.common.code.tour.TourType;
import lombok.Getter;

@Getter
public class TourQueryRequest {

    private final String cursor;
    private final TourType type;

    public TourQueryRequest(String cursor, TourType type) {
        this.cursor = cursor;
        this.type = type;
    }
}
