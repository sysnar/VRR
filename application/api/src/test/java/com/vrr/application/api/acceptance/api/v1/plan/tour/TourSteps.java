package com.vrr.application.api.acceptance.api.v1.plan.tour;

import com.vrr.application.api.acceptance.api.v1.auth.AuthSteps;
import com.vrr.common.code.tour.TourType;
import com.vrr.domain.fixtures.TourFixtures;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.vrr.application.api.acceptance.api.v1.auth.AuthSteps.USER_HAVE_SINGED_IN;

public class TourSteps {

    public static Long TOUR_HAVE_CREATED() {
        String accessToken = USER_HAVE_SINGED_IN(AuthSteps.USERNAME, AuthSteps.EMAIL, AuthSteps.PASSWORD);
        ExtractableResponse<Response> response = TourSteps.TOUR_CREATE_REQUEST(TourFixtures.TITLE, TourFixtures.SUMMARY, TourFixtures.TYPE, TourFixtures.DEPARTURE_POINT, TourFixtures.ARRIVAL_POINT, TourFixtures.START_AT, TourFixtures.END_AT, accessToken);
        return response.jsonPath().getLong("tourId");
    }

    public static ExtractableResponse<Response> TOUR_CREATE_REQUEST(String title, String summary, TourType type, String departurePoint, String arrivalPoint, LocalDateTime startAt, LocalDateTime endAt, String accessToken) {
        Map<String, String> params = new HashMap<>();
        params.put("title", title);
        params.put("summary", summary);
        params.put("type", type.toString());
        params.put("departurePoint", departurePoint);
        params.put("arrivalPoint", arrivalPoint);
        params.put("startAt", startAt.toString());
        params.put("endAt", endAt.toString());

        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post("/api/v1/plan/tour")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
    }

    public static ExtractableResponse<Response> TOUR_DELETE_REQUEST(Long tourId, String accessToken) {
        Map<String, Long> params = new HashMap<>();
        params.put("tourId", tourId);

        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParams(params)
                .when().delete("/api/v1/plan/tour/{tourId}")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .extract();
    }

    public static ExtractableResponse<Response> TOUR_LIST_REQUEST() {
        Map<String, String> params = new HashMap<>();
        return RestAssured
                .given().log().all()
                .when().get("/api/v1/plan/tours")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }

    public static ExtractableResponse<Response> TOUR_LIST_SECOND_REQUEST(String cursor) {
        Map<String, String> params = new HashMap<>();
        params.put("cursor", cursor);

        return RestAssured
                .given().log().all()
                .queryParams(params)
                .when().get("/api/v1/plan/tours")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }
}
