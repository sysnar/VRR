package com.vrr.application.api.domain.tour.api.v1.tour;

import com.vrr.application.api.domain.tour.service.TourListAggregator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tour")
public class TourQueryController {

    private final TourListAggregator tourListAggregator;

    @GetMapping
    public ListTourResponse test() {
        return tourListAggregator.listTours();
    }
}
