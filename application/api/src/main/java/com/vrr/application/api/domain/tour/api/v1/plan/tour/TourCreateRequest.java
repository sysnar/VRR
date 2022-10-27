package com.vrr.application.api.domain.tour.api.v1.plan.tour;

import com.vrr.common.code.tour.TourType;
import com.vrr.domain.tour.domain.Tour;
import com.vrr.domain.tour.domain.TourGroup;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
public class TourCreateRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Summary is required")
    private String summary;

    @NotNull(message = "Tour type is required")
    private TourType type;
    @NotBlank(message = "DeparturePoint is required")
    private String departurePoint;
    @NotNull(message = "ArrivalPoint is required")
    private String arrivalPoint;
    @NotNull(message = "Start date is required")
    private LocalDateTime startAt;

    public Tour toEntity(LocalDateTime now) {
        return Tour.builder()
                .title(title)
                .summary(summary)
                .type(type)
                .departurePoint(departurePoint)
                .arrivalPoint(arrivalPoint)
                .startAt(startAt)
                .createdAt(now)
                .updatedAt(now)
                .open(true)
                .tourGroup(new TourGroup())
                .build();
    }
}
