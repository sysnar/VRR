package com.vrr.application.api.domain.tour.repository;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
public class TourData {

    private Long id;
    private String title;

    @QueryProjection
    public TourData(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
