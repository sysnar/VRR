package com.vrr.application.api.domain.tour.api.v1.tour;

import com.vrr.application.api.domain.tour.repository.TourData;
import lombok.Getter;

import java.util.List;

@Getter
public class ListTourResponse {

    private final List<TourData> tours;

    public ListTourResponse(List<TourData> tours) {
        this.tours = tours;
    }
}
