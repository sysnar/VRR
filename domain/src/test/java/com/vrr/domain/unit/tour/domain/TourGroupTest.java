package com.vrr.domain.unit.tour.domain;

import com.vrr.common.code.tour.MemberType;
import com.vrr.common.code.tour.TourType;
import com.vrr.domain.tour.domain.Tour;
import com.vrr.domain.tour.domain.TourGroup;
import com.vrr.domain.tour.domain.TourMember;
import com.vrr.domain.tour.domain.TourRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.vrr.domain.Fixtures.aTour;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TourGroupTest {

    @Autowired
    private TourRepository tourRepository;

    @AfterEach
    public void afterEach() {
        tourRepository.deleteAll();
    }

    @Test
    void add_tour_organizer() {
        // given
        TourMember leader = TourMember.builder()
                .userId(1L)
                .type(MemberType.LEADER)
                .build();
        Tour tour = aTour()
                .tourGroup(new TourGroup(leader))
                .title("삼각지로 바이크 타고 카페 나들이!")
                .type(TourType.SLOW)
                .build();

        // when
        tourRepository.save(tour);

        // then
        Tour savedTour = tourRepository.findAll().get(0);
        assertThat(savedTour.getLeader().getUserId()).isEqualTo(1L);
    }
}