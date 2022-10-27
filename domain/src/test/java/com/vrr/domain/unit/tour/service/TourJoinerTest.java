package com.vrr.domain.unit.tour.service;

import com.vrr.common.code.tour.MemberType;
import com.vrr.domain.auth.domain.User;
import com.vrr.domain.auth.domain.UserRepository;
import com.vrr.domain.tour.domain.*;
import com.vrr.domain.tour.service.TourJoiner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static com.vrr.domain.fixtures.TourFixtures.*;
import static com.vrr.domain.fixtures.UserFixtures.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TourJoinerTest {

    @Autowired private TourRepository tourRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private TourMemberRepository tourMemberRepository;

    private final LocalDateTime joinDateTime = LocalDateTime.now();
    private TourJoiner tourJoiner;
    private User user;
    private Tour tour;

    @BeforeEach
    public void beforeEach() {
        this.tourJoiner = new TourJoiner(userRepository, tourRepository);
        this.user = userRepository.save(aUser().build());
        this.tour = tourRepository.save(aTour().build());
        this.tour.addMember(this.user, MemberType.LEADER, LocalDateTime.now());
    }

    @Test
    void Should_JoinTour() {
        // given
        User member = userRepository.save(aUser().email("member@gmail.com").serialNumber("UNIQUE_SERIAL").build());
        String serialNumber = member.getSerialNumber();
        Long tourId = tour.getId();

        // when
        tourJoiner.join(tourId, serialNumber, joinDateTime);

        // then
        List<TourMember> tourMembers = tourMemberRepository.findAll();
        assertThat(tourMembers).hasSize(2);
        assertThat(tourMembers).extracting("userId").contains(user.getId(), member.getId());
    }

    @Test
    void Should_ThrowException_When_UserAlreadyJoined() {
        // given
        User member = userRepository.save(aUser().email("member@gmail.com").serialNumber("UNIQUE_SERIAL").build());
        String serialNumber = member.getSerialNumber();
        Long tourId = tour.getId();

        // when
        tourJoiner.join(tourId, serialNumber, joinDateTime);

        // then
        assertThrows(
                IllegalArgumentException.class,
                () -> tourJoiner.join(tourId, serialNumber, joinDateTime)
        );
    }
}