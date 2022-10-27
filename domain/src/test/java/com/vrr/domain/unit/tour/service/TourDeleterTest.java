package com.vrr.domain.unit.tour.service;

import com.vrr.common.code.tour.MemberType;
import com.vrr.domain.auth.domain.User;
import com.vrr.domain.auth.domain.UserRepository;
import com.vrr.domain.tour.domain.Tour;
import com.vrr.domain.tour.domain.TourRepository;
import com.vrr.domain.tour.service.TourDeleteValidator;
import com.vrr.domain.tour.service.TourDeleter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static com.vrr.domain.fixtures.TourFixtures.*;
import static com.vrr.domain.fixtures.UserFixtures.aUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class TourDeleterTest {

    @Autowired private TourRepository tourRepository;
    @Autowired private UserRepository userRepository;

    private final LocalDateTime deleteTime = LocalDateTime.now();
    private TourDeleter tourDeleter;
    private User user;
    private Tour tour;

    @BeforeEach
    public void beforeEach() {
        this.tourDeleter = new TourDeleter(tourRepository, new TourDeleteValidator(userRepository));
        this.user = userRepository.save(aUser().build());
        this.tour = tourRepository.save(aTour().build());
        this.tour.addMember(this.user, MemberType.LEADER, LocalDateTime.now());
    }

    @Test
    void Should_SoftDeleteTour() {
        // given
        String serialNumber = user.getSerialNumber();
        Long tourId = tour.getId();

        // when
        tourDeleter.delete(serialNumber, tourId, deleteTime);

        // then
        Tour deletedTour = tourRepository.findAll().get(0);
        assertThat(deletedTour.getDeletedAt()).isEqualTo(deleteTime);
    }

    @Test
    void Should_Fail_WhenTourAlreadyDeleted() {
        // given
        String serialNumber = user.getSerialNumber();
        Long tourId = tour.getId();

        // when
        tourDeleter.delete(serialNumber, tourId, deleteTime);

        // then
        assertThrows(
                IllegalStateException.class,
                () -> tourDeleter.delete(serialNumber, tourId, deleteTime));
    }

    @Test
    void Should_Fail_WhenNoneLeaderRequest() {
        // given
        String serialNumber = "none-leader-serial";
        Long tourId = tour.getId();

        // when & then
        assertThrows(
                IllegalArgumentException.class,
                () -> tourDeleter.delete(serialNumber, tourId, deleteTime));
    }
}