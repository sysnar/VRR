package com.vrr.application.api.domain.tour.repository;

import com.querydsl.core.annotations.QueryProjection;
import com.vrr.common.code.tour.TourType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TourData {

    private Long id;
    private String title;
    private String summary;
    private TourType type;
    private String departurePoint;
    private String arrivalPoint;
    private LocalDateTime startAt;
    private String leaderUsername;

    @QueryProjection
    public TourData(Long id, String title, String summary, TourType type, String departurePoint, String arrivalPoint, LocalDateTime startAt, String leaderUsername) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.type = type;
        this.departurePoint = departurePoint;
        this.arrivalPoint = arrivalPoint;
        this.startAt = startAt;
        this.leaderUsername = leaderUsername;
    }
}
