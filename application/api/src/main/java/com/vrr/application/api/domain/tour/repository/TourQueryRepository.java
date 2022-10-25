package com.vrr.application.api.domain.tour.repository;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.vrr.common.code.tour.TourType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.vrr.domain.auth.domain.QUser.user;
import static com.vrr.domain.tour.domain.QTour.tour;
import static com.vrr.domain.tour.domain.QTourMember.tourMember;

@Repository
@RequiredArgsConstructor
public class TourQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<TourData> findTours(String cursor, TourType tourType, Pageable pageable) {
        List<TourData> tourData = queryFactory.select(new QTourData(tour.id, tour.title, tour.summary, tour.type, tour.departurePoint, tour.arrivalPoint, tour.startAt, user.username))
                .from(tour)
                .innerJoin(tour.tourGroup.members, tourMember)
                .innerJoin(user).on(user.id.eq(tourMember.userId))
                .where(
                        computeCursor(cursor),
                        matchTourType(tourType),
                        isTourOpen()
                )
                .orderBy(tour.startAt.asc(), tour.id.desc())
                .limit(pageable.getPageSize())
                .fetch();
        return PageableExecutionUtils.getPage(tourData, pageable, tourData::size);
    }

    private BooleanExpression computeCursor(String cursor) {
        if (cursor == null) {
            return null;
        }

        StringTemplate stringTemplate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})",
                tour.startAt,
                ConstantImpl.create("%Y%m%d%H%i%s")
        );

        return StringExpressions.lpad(stringTemplate, 20, '0')
                .concat(StringExpressions.lpad(tour.id.stringValue(), 10, '0'))
                .lt(cursor);
    }

    private BooleanExpression matchTourType(TourType tourType) {
        if (tourType == null) {
            return null;
        }

        return tour.type.eq(tourType);
    }

    private BooleanExpression isTourOpen() {
        return tour.open.isTrue();
    }
}
