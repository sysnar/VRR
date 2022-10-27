package com.vrr.application.api.acceptance.api.v1.plan.member;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

public class MemberSteps {

    public static ExtractableResponse<Response> TOUR_JOIN_REQUEST(Long tourId, String accessToken) {
        Map<String, Long> params = new HashMap<>();
        params.put("tourId", tourId);

        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post("/api/v1/plan/member")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
    }

}
