package com.vrr.domain.tour.domain;

import com.vrr.common.code.tour.MemberType;
import com.vrr.common.code.tour.TourType;
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
    void 투어_주최자_등록() {
        // given
        Tour savedTour = tourRepository.save(aTour()
                .title("삼각지로 바이크 타고 카페 나들이!")
                .type(TourType.SLOW)
                .build());
        TourMember leader = TourMember.builder()
                .tour(savedTour)
                .userId(1L)
                .type(MemberType.LEADER)
                .build();

        // when
        savedTour.addMember(leader);

        // then
        Tour tour = tourRepository.findAll().get(0);
        assertThat(tour.getLeader().getUserId()).isEqualTo(1L);
    }
}