package com.vrr.application.api.domain.auth.controller;

import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vrr.application.api.domain.auth.dto.AuthSignInRequest;
import com.vrr.application.api.domain.auth.dto.AuthSignInResponse;
import com.vrr.application.api.domain.auth.dto.AuthSignUpRequest;
import com.vrr.application.api.domain.auth.service.AuthResolver;
import com.vrr.application.api.domain.auth.service.UserCreator;
import com.vrr.application.api.global.libs.ApiDocumentUtils;
import com.vrr.domain.entity.auth.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureRestDocs
public class AuthSignUpControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean
    private UserCreator userCreator;

    @MockBean
    private AuthResolver authResolver;

    @Test
    @WithMockUser(roles = "USER")
    void 회원가입_정상요청_테스트() throws Exception {
        // given
        HashMap<String, String> body = new HashMap<>();
        body.put("email", "ksy@cmcom.kr");
        body.put("password", "helloWorld22!!");
        body.put("username", "sysnar");

        // when
        given(userCreator.create(any())).willReturn(new User());

        // then
        mockMvc.perform(post("/api/1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(objectMapper.writeValueAsString(body))
                )
                .andExpect(status().isCreated())
                .andDo(document("v1-auth-signUp",
                        ApiDocumentUtils.getDocumentRequest(),
                        ApiDocumentUtils.getDocumentResponse(),
                        ResourceDocumentation.resource(
                                ResourceSnippetParameters.builder()
                                        .tag("인증 API")
                                        .summary("회원가입 API")
                                        .description("OAuth2 로그인 등이 실패할 경우, 아이디/비밀번호 기반의 회원가입을 지원하는 API 입니다.")
                                        .requestFields(
                                                fieldWithPath("email").type(JsonFieldType.STRING).description("회원가입하길 원하는 아이디(이메일)"),
                                                fieldWithPath("password").type(JsonFieldType.STRING).description("회원가입하길 원하는 비밀번호"),
                                                fieldWithPath("username").type(JsonFieldType.STRING).description("회원가입하길 원하는 사용자 닉네임")
                                        )
                                        .build()
                        ),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("회원가입하길 원하는 아이디(이메일)"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("회원가입하길 원하는 비밀번호"),
                                fieldWithPath("username").type(JsonFieldType.STRING).description("회원가입하길 원하는 사용자 닉네임")
                        )
                ));
    }
}
