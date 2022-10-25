package com.vrr.application.api.domain.tour.api.v1.tour;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.vrr.application.api.domain.tour.repository.TourData;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@JsonInclude(Include.NON_NULL)
public class TourQueryResponse {

    private final List<TourData> tours;
    private final int resultCount;
    private final String next;

    public TourQueryResponse(Page<TourData> tours, String next) {
        this.tours = tours.getContent();
        this.resultCount = tours.getNumberOfElements();
        this.next = next;
    }
}
