package com.vrr.application.api.domain.tour.service;

import com.vrr.application.api.domain.tour.api.v1.tour.TourQueryRequest;
import com.vrr.application.api.domain.tour.api.v1.tour.TourQueryResponse;
import com.vrr.application.api.domain.tour.repository.TourData;
import com.vrr.application.api.domain.tour.repository.TourQueryRepository;
import com.vrr.common.annotation.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationService
@RequiredArgsConstructor
public class TourListAggregator {

    private final TourQueryRepository tourQueryRepository;

    public TourQueryResponse listTours(TourQueryRequest tourQueryRequest, Pageable pageable) {
        Page<TourData> tours = tourQueryRepository.findTours(tourQueryRequest.getCursor(), tourQueryRequest.getType(), pageable);
        TourData lastTourData = getLastTourData(tours);
        return new TourQueryResponse(tours, generateCursor(lastTourData));
    }

    private TourData getLastTourData(Page<TourData> tourData) {
        List<TourData> contents = tourData.getContent();
        if (contents.isEmpty()) {
            return null;
        }
        return contents.get(contents.size() - 1);
    }

    private String generateCursor(TourData lastTourData) {
        if (lastTourData == null) {
            return null;
        }
        String customCursorStartDate;
        String customCursorId;

        LocalDateTime cursorStartAt = lastTourData.getStartAt().minusHours(9); // UTC Timezone Setting
        customCursorStartDate = cursorStartAt.toString()
                .replace("T", "")
                .replace("-", "")
                .replace(":", "") + "00";

        customCursorStartDate = String.format("%1$" + 20 + "s", customCursorStartDate)
                .replace(' ', '0');

        customCursorId = String.format("%1$" + 10 + "s", lastTourData.getId())
                .replace(' ', '0');

        return customCursorStartDate + customCursorId;
    }
}
