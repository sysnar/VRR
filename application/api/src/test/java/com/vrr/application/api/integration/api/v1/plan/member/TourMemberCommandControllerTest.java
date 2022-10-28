package com.vrr.application.api.integration.api.v1.plan.member;

import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.SimpleType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vrr.application.api.global.libs.AccessTokenUtils;
import com.vrr.application.api.global.libs.ApiDocumentUtils;
import com.vrr.domain.tour.domain.Tour;
import com.vrr.domain.tour.service.TourCreator;
import com.vrr.domain.tour.service.TourDeleter;
import com.vrr.domain.tour.service.TourJoiner;
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
class TourMemberCommandControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean
    private TourJoiner tourJoiner;

    @Test
    void Request_Tour_Join_Success() throws Exception {
        // given
        String token = AccessTokenUtils.createToken();
        HashMap<Object, Object> body = new HashMap<>();
        body.put("tourId", 1L);

        // when
        willDoNothing().given(tourJoiner).join(any(), any(), any());

        // then
        mockMvc.perform(post("/api/v1/plan/member")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                )
                .andExpect(status().isCreated())
                .andDo(document("v1/plan/member-post",
                        ApiDocumentUtils.getDocumentRequest(),
                        ApiDocumentUtils.getDocumentResponse(),
                        ResourceDocumentation.resource(
                                ResourceSnippetParameters.builder()
                                        .tag("투어 멤버 API")
                                        .summary("투어 참가 API")
                                        .description("투어에 참가자로 참가하는 API 입니다.")
                                        .requestHeaders(headerWithName("Authorization").type(SimpleType.STRING).description("Basic auth credentials"))
                                        .requestFields(fieldWithPath("tourId").type(JsonFieldType.NUMBER).description("투어 고유 ID"))
                                        .build()
                        ),
                        requestHeaders(HeaderDocumentation.headerWithName("Authorization").description("Basic auth credentials")),
                        requestFields(fieldWithPath("tourId").type(JsonFieldType.NUMBER).description("투어 고유 ID"))
                ));
    }
}
