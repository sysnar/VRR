package com.vrr.application.api.domain.tour.api.v1.plan.member;

import com.vrr.application.api.global.auth.annotation.CurrentUserSerial;
import com.vrr.domain.tour.service.TourJoiner;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/plan/member")
public class TourMemberCommandController {

    private final TourJoiner tourJoiner;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void joinTour(@Valid @RequestBody TourJoinRequest tourJoinRequest,
                         @CurrentUserSerial String userSerial) {
        tourJoiner.join(tourJoinRequest.getTourId(), userSerial, LocalDateTime.now());
    }
}
