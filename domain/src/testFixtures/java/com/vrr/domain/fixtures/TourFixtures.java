package com.vrr.domain.fixtures;

import com.vrr.common.code.tour.TourType;
import com.vrr.domain.tour.domain.Tour;
import com.vrr.domain.tour.domain.TourGroup;

import java.time.LocalDateTime;

public class TourFixtures {

    public static String TITLE = "삼각지로 바이크 타고 카페 나들이!";
    public static String SUMMARY = "주말인데 삼각지에 카페타고 이쁜 카페가보실 분 있으면 같이 가요~";
    public static TourType TYPE = TourType.SLOW;
    public static String DEPARTURE_POINT = "강남역,선릉역";
    public static String ARRIVAL_POINT = "서울특별시 용산구 한강대로 62나길 7-4";
    public static LocalDateTime START_AT = LocalDateTime.of(2022, 8, 13, 12, 0);
    public static LocalDateTime END_AT = LocalDateTime.of(2022, 8, 13, 18, 0);

    public static Tour.TourBuilder aTour() {
        return Tour.builder()
                .tourGroup(new TourGroup())
                .title("삼각지로 바이크 타고 카페 나들이!")
                .summary("주말인데 삼각지에 카페타고 이쁜 카페가보실 분 있으면 같이 가요~")
                .type(TourType.SLOW)
                .open(true)
                .departurePoint("강남역,선릉역")
                .arrivalPoint("서울특별시 용산구 한강대로 62나길 7-4")
                .startAt(LocalDateTime.of(2022, 8, 13, 12, 0));
    }
}
