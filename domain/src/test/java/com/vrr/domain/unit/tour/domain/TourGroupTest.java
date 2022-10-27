package com.vrr.domain.unit.tour.domain;

import com.vrr.common.code.tour.MemberType;
import com.vrr.common.code.tour.TourType;
import com.vrr.domain.auth.domain.User;
import com.vrr.domain.auth.domain.UserRepository;
import com.vrr.domain.tour.domain.Tour;
import com.vrr.domain.tour.domain.TourGroup;
import com.vrr.domain.tour.domain.TourRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static com.vrr.domain.fixtures.TourFixtures.aTour;
import static com.vrr.domain.fixtures.UserFixtures.aUser;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TourGroupTest {

    @Autowired private TourRepository tourRepository;
    @Autowired private UserRepository userRepository;

    @AfterEach
    public void afterEach() {
        tourRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void add_tour_organizer() {
        // given
        User user = userRepository.save(aUser().email("tour_organizer@gmail.com").build());
        Tour tour = aTour()
                .tourGroup(new TourGroup())
                .title("삼각지로 바이크 타고 카페 나들이!")
                .type(TourType.SLOW)
                .build();
        tour.addMember(user, MemberType.LEADER, LocalDateTime.now());

        // when
        tourRepository.save(tour);

        // then
        Long userId = tourRepository.findAll().get(0).getLeader().getUserId();
        assertThat(userRepository.findById(userId).orElseThrow().getEmail())
                .isEqualTo("tour_organizer@gmail.com");
    }
}