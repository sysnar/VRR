package com.vrr.application.api.acceptance.api.v1.plan.tour;

import com.vrr.application.api.acceptance.api.v1.auth.AuthSteps;
import com.vrr.domain.fixtures.TourFixtures;
import com.vrr.domain.utils.AcceptanceTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;

import static com.vrr.application.api.acceptance.api.v1.auth.AuthSteps.USER_HAVE_SINGED_IN;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("Tour Manage API")
class TourCommandControllerTest extends AcceptanceTest {

    /**
     * Given : After user signed up.
     * When : User can create tour
     * Then : And tour could be found at tour list
     */
    @Test
    void Should_CreateTour() {
        // given
        String accessToken = USER_HAVE_SINGED_IN(AuthSteps.USERNAME, AuthSteps.EMAIL, AuthSteps.PASSWORD);

        // when
        ExtractableResponse<Response> response = TourSteps.TOUR_CREATE_REQUEST(TourFixtures.TITLE, TourFixtures.SUMMARY, TourFixtures.TYPE, TourFixtures.DEPARTURE_POINT, TourFixtures.ARRIVAL_POINT, TourFixtures.START_AT, TourFixtures.END_AT, accessToken);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        ExtractableResponse<Response> listResponse = TourSteps.TOUR_LIST_REQUEST();

        assertThat(listResponse.jsonPath().getList("tours.id")).contains(1);
        assertThat(listResponse.jsonPath().getList("tours.title")).contains("삼각지로 바이크 타고 카페 나들이!");
    }

    /**
     * Given : After tour created.
     * When : Leader delete tour
     * Then : Tour should be soft-deleted and cannot be found on list
     */
    @Test
    void Should_DeleteTour() {
        // given
        String accessToken = USER_HAVE_SINGED_IN(AuthSteps.USERNAME, AuthSteps.EMAIL, AuthSteps.PASSWORD);
        ExtractableResponse<Response> createResponse = TourSteps.TOUR_CREATE_REQUEST(TourFixtures.TITLE, TourFixtures.SUMMARY, TourFixtures.TYPE, TourFixtures.DEPARTURE_POINT, TourFixtures.ARRIVAL_POINT, TourFixtures.START_AT, TourFixtures.END_AT, accessToken);
        long tourId = createResponse.jsonPath().getLong("tourId");

        // when
        ExtractableResponse<Response> response = TourSteps.TOUR_DELETE_REQUEST(tourId, accessToken);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        Assertions.assertThat(TourSteps.TOUR_LIST_REQUEST().jsonPath().getList("tours")).isEmpty();
    }
}