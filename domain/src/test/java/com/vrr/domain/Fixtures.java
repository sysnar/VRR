package com.vrr.domain;

import com.vrr.common.code.auth.ProviderType;
import com.vrr.common.code.auth.RoleType;
import com.vrr.common.code.tour.TourType;
import com.vrr.domain.auth.domain.User;
import com.vrr.domain.auth.domain.User.UserBuilder;
import com.vrr.domain.tour.domain.Tour;
import com.vrr.domain.tour.domain.Tour.TourBuilder;
import com.vrr.domain.tour.domain.TourGroup;

import java.time.LocalDateTime;

public class Fixtures {

    public static UserBuilder aUser() {
        return User.builder()
                .serialNumber("22d4a8bc-9478-46b4-80cf-09e2bbbfb5a4")
                .email("sysnar@gmail.com")
                .emailVerified("Y")
                .password("{bcrypt}as18j12389AS!132")
                .username("sysnar")
                .profileImageUrl("https://avatars.githubusercontent.com/u/85546022?v=4")
                .providerType(ProviderType.VRR)
                .roleType(RoleType.USER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now());
    }

    public static TourBuilder aTour() {
        return Tour.builder()
                .tourGroup(new TourGroup())
                .title("삼각지로 바이크 타고 카페 나들이!")
                .summary("주말인데 삼각지에 카페타고 이쁜 카페가보실 분 있으면 같이 가요~")
                .type(TourType.SLOW)
                .open(true)
                .departure("강남역,선릉역")
                .arrival("서울특별시 용산구 한강대로 62나길 7-4")
                .startAt(LocalDateTime.of(2022, 8, 13, 12, 0));
    }
}
