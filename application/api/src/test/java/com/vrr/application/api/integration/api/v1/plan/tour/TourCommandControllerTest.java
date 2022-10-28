package com.vrr.application.api.integration.api.v1.plan.tour;

import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.SimpleType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vrr.application.api.global.libs.AccessTokenUtils;
import com.vrr.application.api.global.libs.ApiDocumentUtils;
import com.vrr.domain.tour.domain.Tour;
import com.vrr.domain.tour.service.TourCreator;
import com.vrr.domain.tour.service.TourDeleter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.headers.HeaderDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import java.util.HashMap;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ActiveProfiles("test")
class TourCommandControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean
    private TourCreator tourCreator;

    @MockBean
    private TourDeleter tourDeleter;

    @Test
    void Request_Tour_Create_Success() throws Exception {
        // given
        String token = AccessTokenUtils.createToken();
        HashMap<Object, Object> body = new HashMap<>();
        body.put("title", "제주도 해안도로 고고");
        body.put("summary", "함께 제주도 해안도로를 달려보아요");
        body.put("type", "SLOW");
        body.put("departurePoint", "제주도 서귀포 공항");
        body.put("arrivalPoint", "한라산 주차장");
        body.put("startAt",  "2022-08-13T12:00:00");

        // when
        given(tourCreator.create(any(), any(), any())).willReturn(Tour.builder().id(1L).build());

        // then
        mockMvc.perform(post("/api/v1/plan/tour")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                )
                .andExpect(status().isCreated())
                .andDo(document("v1/plan/tour-post",
                        ApiDocumentUtils.getDocumentRequest(),
                        ApiDocumentUtils.getDocumentResponse(),
                        ResourceDocumentation.resource(
                                ResourceSnippetParameters.builder()
                                        .tag("투어 API")
                                        .summary("투어 생성 API")
                                        .description("투어를 생성하는 API 입니다.")
                                        .requestHeaders(headerWithName("Authorization").type(SimpleType.STRING).description("Basic auth credentials"))
                                        .requestFields(
                                                fieldWithPath("title").type(JsonFieldType.STRING).description("투어 제목"),
                                                fieldWithPath("summary").type(JsonFieldType.STRING).description("투어 설명"),
                                                fieldWithPath("type").type(JsonFieldType.STRING).description("투어 타입 (SLOW, FAST, SUPER_FAST)"),
                                                fieldWithPath("departurePoint").type(JsonFieldType.STRING).description("투어 출발지"),
                                                fieldWithPath("arrivalPoint").type(JsonFieldType.STRING).description("투어 도착지"),
                                                fieldWithPath("startAt").type(JsonFieldType.STRING).description("투어 시작일시"))
                                        .responseFields(fieldWithPath("tourId").type(JsonFieldType.NUMBER).description("생성된 투어 고유 ID"))
                                        .build()
                        ),
                        requestHeaders(HeaderDocumentation.headerWithName("Authorization").description("Basic auth credentials")),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("투어 제목"),
                                fieldWithPath("summary").type(JsonFieldType.STRING).description("투어 설명"),
                                fieldWithPath("type").type(JsonFieldType.STRING).description("투어 타입 (SLOW, FAST, SUPER_FAST)"),
                                fieldWithPath("departurePoint").type(JsonFieldType.STRING).description("투어 출발지"),
                                fieldWithPath("arrivalPoint").type(JsonFieldType.STRING).description("투어 도착지"),
                                fieldWithPath("startAt").type(JsonFieldType.STRING).description("투어 시작일시")),
                        responseFields(fieldWithPath("tourId").type(JsonFieldType.NUMBER).description("생성된 투어 고유 ID"))
                ));
    }


    @Test
    void Request_Tour_Delete_Success() throws Exception {
        // given
        String token = AccessTokenUtils.createToken();
        String tourId = "1";

        // when
        willDoNothing().given(tourDeleter).delete(any(), any(), any());

        // then
        mockMvc.perform(delete("/api/v1/plan/tour/{tourId}", tourId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
                .andDo(document("v1/plan/tour-id-delete",
                        ApiDocumentUtils.getDocumentRequest(),
                        ApiDocumentUtils.getDocumentResponse(),
                        ResourceDocumentation.resource(
                                ResourceSnippetParameters.builder()
                                        .tag("투어 API")
                                        .summary("투어 삭제 API")
                                        .description("투어를 삭제하는 API 입니다.")
                                        .requestHeaders(headerWithName("Authorization").type(SimpleType.STRING).description("Basic auth credentials"))
                                        .pathParameters(parameterWithName("tourId").type(SimpleType.STRING).description("투어 고유 ID"))
                                        .build()
                        ),
                        requestHeaders(HeaderDocumentation.headerWithName("Authorization").description("Basic auth credentials")),
                        pathParameters(RequestDocumentation.parameterWithName("tourId").description("투어 고유 ID"))
                ));
    }
}
