package com.vrr.application.api.integration.api.v1.plan.tours;

import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.SimpleType;
import com.vrr.application.api.domain.tour.api.v1.plan.tours.ToursQueryResponse;
import com.vrr.application.api.domain.tour.repository.TourData;
import com.vrr.application.api.domain.tour.service.TourListAggregator;
import com.vrr.application.api.global.libs.ApiDocumentUtils;
import com.vrr.domain.fixtures.TourFixtures;
import com.vrr.domain.fixtures.UserFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ActiveProfiles("test")
class ToursQueryControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean
    private TourListAggregator tourListAggregator;

    @Test
    void Request_Tours_Success() throws Exception {
        // given
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cursor", "000000202208130300000000000000");
        List<TourData> tourData = List.of(
                new TourData(1L,
                        TourFixtures.TITLE, TourFixtures.SUMMARY, TourFixtures.TYPE,
                        TourFixtures.DEPARTURE_POINT, TourFixtures.ARRIVAL_POINT,
                        TourFixtures.START_AT, UserFixtures.USERNAME));
        Page<TourData> page = PageableExecutionUtils.getPage(tourData, PageRequest.of(0, 10), tourData::size);
        ToursQueryResponse toursQueryResponse = new ToursQueryResponse(page, "000000202208130300000000000007");

        // when
        given(tourListAggregator.listTours(any(), any())).willReturn(toursQueryResponse);

        // then
        mockMvc.perform(get("/api/v1/plan/tours")
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParams(params)
                )
                .andExpect(status().isOk())
                .andDo(document("v1/plan/tours",
                        ApiDocumentUtils.getDocumentRequest(),
                        ApiDocumentUtils.getDocumentResponse(),
                        ResourceDocumentation.resource(
                                ResourceSnippetParameters.builder()
                                        .tag("투어 API")
                                        .summary("투어 리스트 조회 API")
                                        .description("프로젝트에 존재하는 투어 리스트를 조회하는 API 입니다.")
                                        .requestParameters(parameterWithName("cursor").type(SimpleType.STRING).description("페이지 조회에 사용되는 커서(토큰)").optional())
                                        .responseFields(
                                                fieldWithPath("tours[].id").type(JsonFieldType.NUMBER).description("투어 고유 ID"),
                                                fieldWithPath("tours[].title").type(JsonFieldType.STRING).description("투어 제목"),
                                                fieldWithPath("tours[].summary").type(JsonFieldType.STRING).description("투어 설명"),
                                                fieldWithPath("tours[].type").type(JsonFieldType.STRING).description("투어 타입"),
                                                fieldWithPath("tours[].departurePoint").type(JsonFieldType.STRING).description("투어 출발지"),
                                                fieldWithPath("tours[].arrivalPoint").type(JsonFieldType.STRING).description("투어 도착지"),
                                                fieldWithPath("tours[].startAt").type(JsonFieldType.STRING).description("투어 시작일"),
                                                fieldWithPath("tours[].leaderUsername").type(JsonFieldType.STRING).description("투어 리더"),
                                                fieldWithPath("resultCount").type(JsonFieldType.NUMBER).description("현재 페이지 투어 수"),
                                                fieldWithPath("next").type(JsonFieldType.STRING).description("다음 페이지 조회를 요청할 수 있는 커서(토큰)"))
                                        .build()
                        ),
                        requestParameters(RequestDocumentation.parameterWithName("cursor").description("페이지 조회에 사용되는 커서(토큰)").optional()),
                        responseFields(
                                fieldWithPath("tours[].id").type(JsonFieldType.NUMBER).description("투어 고유 ID"),
                                fieldWithPath("tours[].title").type(JsonFieldType.STRING).description("투어 제목"),
                                fieldWithPath("tours[].summary").type(JsonFieldType.STRING).description("투어 설명"),
                                fieldWithPath("tours[].type").type(JsonFieldType.STRING).description("투어 타입"),
                                fieldWithPath("tours[].departurePoint").type(JsonFieldType.STRING).description("투어 출발지"),
                                fieldWithPath("tours[].arrivalPoint").type(JsonFieldType.STRING).description("투어 도착지"),
                                fieldWithPath("tours[].startAt").type(JsonFieldType.STRING).description("투어 시작일"),
                                fieldWithPath("tours[].leaderUsername").type(JsonFieldType.STRING).description("투어 리더"),
                                fieldWithPath("resultCount").type(JsonFieldType.NUMBER).description("현재 페이지 투어 수"),
                                fieldWithPath("next").type(JsonFieldType.STRING).description("다음 페이지 조회를 요청할 수 있는 커서(토큰)"))
                ));
    }
}
