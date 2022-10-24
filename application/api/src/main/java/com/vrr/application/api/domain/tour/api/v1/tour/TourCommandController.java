package com.vrr.application.api.domain.tour.api.v1.tour;

import com.vrr.application.api.domain.tour.service.TourCreateAggregator;
import com.vrr.application.api.global.auth.annotation.CurrentUserSerial;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tour")
public class TourCommandController {

    private final TourCreateAggregator tourCreateAggregator;

    @PostMapping
    public ResponseEntity<Object> createTour(@CurrentUserSerial String serial, @Valid @RequestBody TourCreateRequest tourCreateRequest) {
        return ResponseEntity.created(URI.create("/api/v1/tour"))
                .body(tourCreateAggregator.create(serial, tourCreateRequest));
    }
}
