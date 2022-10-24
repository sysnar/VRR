package com.vrr.application.api.domain.tour.service;

import com.vrr.application.api.domain.tour.api.v1.tour.ListTourResponse;
import com.vrr.application.api.domain.tour.repository.TourQueryRepository;
import com.vrr.common.annotation.ApplicationService;
import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
public class TourListAggregator {

    private final TourQueryRepository tourQueryRepository;

    public ListTourResponse listTours() {
        return new ListTourResponse(tourQueryRepository.findTours());
    }
}
