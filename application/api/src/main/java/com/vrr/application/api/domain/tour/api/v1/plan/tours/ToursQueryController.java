package com.vrr.application.api.domain.tour.api.v1.plan.tours;

import com.vrr.application.api.domain.tour.service.TourListAggregator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/plan/tours")
public class ToursQueryController {

    private final TourListAggregator tourListAggregator;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ToursQueryResponse findTours(@Valid @ModelAttribute ToursQueryRequest request,
                                        @PageableDefault(size = 10, sort = "endDate", direction = DESC) Pageable pageable) {
        return tourListAggregator.listTours(request, pageable);
    }
}
