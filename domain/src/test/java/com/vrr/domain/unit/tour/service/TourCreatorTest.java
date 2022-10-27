package com.vrr.domain.unit.tour.service;

import com.vrr.common.code.tour.TourType;
import com.vrr.domain.auth.domain.User;
import com.vrr.domain.auth.domain.UserRepository;
import com.vrr.domain.tour.domain.Tour;
import com.vrr.domain.tour.domain.TourRepository;
import com.vrr.domain.tour.service.TourCreator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static com.vrr.domain.fixtures.TourFixtures.aTour;
import static com.vrr.domain.fixtures.UserFixtures.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
class TourCreatorTest {

    @Autowired private UserRepository userRepository;
    @Autowired private TourRepository tourRepository;

    @Test
    void Should_CreateLeader_WhenTourFormIsNormal() {
        // given
        TourCreator tourCreator = new TourCreator(userRepository, tourRepository);
        User user = userRepository.save(aUser().build());
        Tour tour = aTour().build();

        // when
        tourCreator.create(user.getSerialNumber(), tour, LocalDateTime.now());

        // then
        Tour foundTour = tourRepository.findAll().get(0);
        assertThat(foundTour.getType()).isEqualTo(TourType.SLOW);
        assertThat(foundTour.getTitle()).isEqualTo("삼각지로 바이크 타고 카페 나들이!");
    }
}