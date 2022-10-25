package com.vrr.application.api.domain.tour.api.v1.tour;

import com.vrr.common.code.tour.TourType;
import com.vrr.domain.tour.service.TourForm;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
public class TourCreateRequest {

        private String title;

        @NotNull(message = "asdf")
        private String summary;

        @NotNull(message = "asdf")
        private TourType type;
        private String departurePoint;
        private String arrivalPoint;
        private LocalDateTime startAt;

        public TourForm toTourForm(String serial) {
                return new TourForm(
                        serial,
                        this.type,
                        this.title,
                        this.summary,
                        this.departurePoint,
                        this.arrivalPoint,
                        this.startAt,
                        LocalDateTime.now(),
                        LocalDateTime.now()
                );
        }
}
