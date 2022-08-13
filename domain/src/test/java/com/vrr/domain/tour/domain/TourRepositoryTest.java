package com.vrr.domain.tour.domain;

import com.vrr.common.code.tour.TourType;
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
    void 투어_제목_타입_정상조회() {
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
    void 투어_생성일_수정일_정상생성() {
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