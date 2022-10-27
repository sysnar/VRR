package com.vrr.application.api.domain.tour.api.v1.plan.tour;

import com.vrr.application.api.global.auth.annotation.CurrentUserSerial;
import com.vrr.domain.tour.domain.Tour;
import com.vrr.domain.tour.service.TourCreator;
import com.vrr.domain.tour.service.TourDeleter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/plan/tour")
public class TourCommandController {

    private final TourDeleter tourDeleter;
    private final TourCreator tourCreator;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TourCreateResponse createTour(@CurrentUserSerial String userSerial, @Valid @RequestBody TourCreateRequest tourCreateRequest) {
        Tour tour = tourCreateRequest.toEntity(LocalDateTime.now());
        return TourCreateResponse.of(tourCreator.create(userSerial, tour, LocalDateTime.now()));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{tourId}")
    public void deleteTour(@CurrentUserSerial String serial, @PathVariable("tourId") Long tourId) {
        tourDeleter.delete(serial, tourId, LocalDateTime.now());
    }
}
