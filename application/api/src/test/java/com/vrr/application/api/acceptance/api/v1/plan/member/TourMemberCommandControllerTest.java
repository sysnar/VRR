package com.vrr.application.api.acceptance.api.v1.plan.member;

import com.vrr.domain.utils.AcceptanceTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.vrr.application.api.acceptance.api.v1.auth.AuthSteps.*;
import static com.vrr.application.api.acceptance.api.v1.plan.member.MemberSteps.TOUR_JOIN_REQUEST;
import static com.vrr.application.api.acceptance.api.v1.plan.tour.TourSteps.TOUR_HAVE_CREATED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tour Member Command API")
class TourMemberCommandControllerTest extends AcceptanceTest {

    /**
     * Given : After tour have created & user signed In.
     * When : User request to join tour,
     * Then : user became a tour member.
     */
    @Test
    void Should_CreateTour() {
        // given
        Long tourId = TOUR_HAVE_CREATED();
        String accessToken = USER_HAVE_SINGED_IN(MEMBER_USERNAME, MEMBER_EMAIL, MEMBER_PASSWORD);

        // when
        ExtractableResponse<Response> response = TOUR_JOIN_REQUEST(tourId, accessToken);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

}