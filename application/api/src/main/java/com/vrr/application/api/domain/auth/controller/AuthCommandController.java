package com.vrr.application.api.domain.auth.controller;

import com.vrr.application.api.domain.auth.dto.AuthSignInRequest;
import com.vrr.application.api.domain.auth.dto.AuthSignInResponse;
import com.vrr.application.api.domain.auth.dto.AuthSignUpRequest;
import com.vrr.application.api.domain.auth.service.UserCreator;
import com.vrr.application.api.domain.auth.service.AuthResolver;
import com.vrr.domain.entity.auth.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/1/auth")
public class AuthCommandController {

    private final UserCreator userCreator;
    private final AuthResolver authResolver;

    @PostMapping("/signin")
    public ResponseEntity<AuthSignInResponse> signIn(HttpServletRequest request,
                                 HttpServletResponse response,
                                 @Validated @RequestBody AuthSignInRequest signInRequest) {
        return ResponseEntity.created(URI.create("/signIn"))
                .body(authResolver.signIn(request, response, signInRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity signUp(@Validated @RequestBody AuthSignUpRequest signUpRequest) {
        User newUser = userCreator.create(signUpRequest);
        return ResponseEntity.created(URI.create("/signup/" + newUser.getId()))
                .body(null);
    }
}
