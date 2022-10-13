package com.vrr.domain.unit.tour.domain;

import com.vrr.common.code.tour.TourType;
import com.vrr.domain.tour.domain.Tour;
import com.vrr.domain.tour.domain.TourRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static com.vrr.domain.Fixtures.aTour;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TourRepositoryTest {

    @Autowired
    private TourRepository tourRepository;

    @AfterEach
    public void afterEach() {
        tourRepository.deleteAll();
    }

    @Test
    void find_tour_title_type_() {
        // given
        tourRepository.save(aTour()
                .title("삼각지로 바이크 타고 카페 나들이!")
                .type(TourType.SLOW)
                .build());

        // when
        List<Tour> tours = tourRepository.findAll();

        // then
        Tour tour = tours.get(0);
        assertThat(tour.getTitle()).isEqualTo("삼각지로 바이크 타고 카페 나들이!");
        assertThat(tour.getType()).isEqualTo(TourType.SLOW);
    }

    @Test
    void create_tour_with_createdAt_updatedAt() {
        // given
        LocalDateTime now = LocalDateTime.now();
        tourRepository.save(aTour().build());

        // when
        List<Tour> tours = tourRepository.findAll();

        // then
        Tour tour = tours.get(0);
        assertThat(tour.getCreatedAt()).isAfter(now);
        assertThat(tour.getUpdatedAt()).isAfter(now);
        assertThat(tours).hasSize(1);
    }
}