package com.vrr.application.api.acceptance.api.v1.tour;

import com.vrr.application.api.acceptance.api.v1.auth.AuthSteps;
import com.vrr.domain.fixtures.TourFixtures;
import com.vrr.domain.utils.AcceptanceTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

import static com.vrr.application.api.acceptance.api.v1.auth.AuthSteps.USER_HAVE_SINGED_IN;
import static com.vrr.application.api.acceptance.api.v1.tour.TourSteps.*;
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
        ExtractableResponse<Response> response = TOUR_CREATE_REQUEST(TourFixtures.TITLE, TourFixtures.SUMMARY, TourFixtures.TYPE, TourFixtures.DEPARTURE_POINT, TourFixtures.ARRIVAL_POINT, TourFixtures.START_AT, TourFixtures.END_AT, accessToken);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        ExtractableResponse<Response> listResponse = TOUR_LIST_REQUEST();

        assertThat(listResponse.jsonPath().getList("tours.id")).contains(1);
        assertThat(listResponse.jsonPath().getList("tours.title")).contains("삼각지로 바이크 타고 카페 나들이!");
    }

    /**
     * Given : After 2 Tour have created.
     * When : Tour list have looked
     * Then : 2 Tour could be found on result.
     */
    @Test
    void Should_FindTour_When_TourIsCreated() {
        // given
        String accessToken = USER_HAVE_SINGED_IN(AuthSteps.USERNAME, AuthSteps.EMAIL, AuthSteps.PASSWORD);
        TOUR_CREATE_REQUEST(TourFixtures.TITLE, TourFixtures.SUMMARY, TourFixtures.TYPE, TourFixtures.DEPARTURE_POINT, TourFixtures.ARRIVAL_POINT, TourFixtures.START_AT, TourFixtures.END_AT, accessToken);
        ExtractableResponse<Response> response = TOUR_CREATE_REQUEST(TourFixtures.TITLE, TourFixtures.SUMMARY, TourFixtures.TYPE, TourFixtures.DEPARTURE_POINT, TourFixtures.ARRIVAL_POINT, TourFixtures.START_AT, TourFixtures.END_AT, accessToken);

        // when
        ExtractableResponse<Response> listResponse = TOUR_LIST_REQUEST();
        String next = listResponse.jsonPath().getString("next");
        assertThat(listResponse.jsonPath().getList("tours")).hasSize(2);
        assertThat(next).isEqualTo("000000202208130300000000000001");

        // then
        ExtractableResponse<Response> emptyResponse = TOUR_LIST_SECOND_REQUEST(next);
        assertThat(emptyResponse.jsonPath().getList("tours")).isEmpty();
        assertThat(emptyResponse.jsonPath().getString("next")).isNull();
    }

    /**
     * Given : After tour have found.
     * When : User request for the specific information of Tour,
     * Then : user can get tour details.
     */
    @Test
    void Should_FindTourDetail_When_TourListIsFound() {
        // given

        // when

        // then
    }
}