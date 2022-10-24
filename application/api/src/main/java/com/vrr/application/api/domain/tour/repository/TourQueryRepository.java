package com.vrr.application.api.domain.tour.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.vrr.domain.tour.domain.Tour;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.vrr.domain.tour.domain.QTour.tour;

@Repository
@RequiredArgsConstructor
public class TourQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<TourData> findTours() {
        return queryFactory.select(new QTourData(tour.id, tour.title))
                .from(tour)
                .fetch();
    }
}
