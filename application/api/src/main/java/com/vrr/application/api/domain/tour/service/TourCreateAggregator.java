package com.vrr.application.api.domain.tour.service;

import com.vrr.application.api.domain.tour.api.v1.tour.TourCreateRequest;
import com.vrr.application.api.domain.tour.api.v1.tour.TourCreateResponse;
import com.vrr.common.annotation.ApplicationService;
import com.vrr.domain.tour.domain.Tour;
import com.vrr.domain.tour.service.TourCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@ApplicationService
@RequiredArgsConstructor
public class TourCreateAggregator {

    @Autowired
    private final TourCreator tourCreator;

    public TourCreateResponse create(String serial, TourCreateRequest tourCreateRequest) {
        Tour tour = tourCreator.create(tourCreateRequest.toTourForm(serial));
        return new TourCreateResponse(tour);
    }
}
