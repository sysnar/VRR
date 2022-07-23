package com.vrr.application.api.domain.auth.service;

import com.vrr.application.api.domain.auth.dto.AuthSignInRequest;
import com.vrr.application.api.domain.auth.dto.AuthSignInResponse;
import com.vrr.application.api.global.auth.properties.AppProperties;
import com.vrr.application.api.global.auth.service.UserPrincipal;
import com.vrr.application.api.global.auth.token.AuthToken;
import com.vrr.application.api.global.auth.token.AuthTokenProvider;
import com.vrr.application.api.global.auth.token.AuthTokens;
import com.vrr.common.annotation.ApplicationService;
import com.vrr.common.auth.utils.CookieUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static com.vrr.application.api.global.auth.repository.HttpCookieOAuth2AuthorizationRequestRepository.REFRESH_TOKEN;

@ApplicationService
@RequiredArgsConstructor
public class AuthResolver {

    private final AuthenticationManager authenticationManager;
    private final AuthTokenProvider tokenProvider;
    private final AppProperties appProperties;

    @Transactional(readOnly = true)
    public AuthSignInResponse signIn(HttpServletRequest request, HttpServletResponse response, AuthSignInRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getEmail(),
                        signInRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AuthTokens tokens = generateTokens(signInRequest, authentication);

        int cookieMaxAge = (int) appProperties.getAuth().getRefreshTokenExpiry() / 60;
        CookieUtils.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtils.addCookie(response, REFRESH_TOKEN, tokens.getRefreshToken().getToken(), cookieMaxAge);

        return new AuthSignInResponse(tokens.getAccessToken().getToken());
    }

    private AuthTokens generateTokens(AuthSignInRequest signInRequest, Authentication authentication) {
        Date now = new Date();
        AuthToken accessToken = tokenProvider.createAuthToken(
                signInRequest.getEmail(),
                ((UserPrincipal) authentication.getPrincipal()).getAuthorities().toString(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
        AuthToken refreshToken = tokenProvider.createAuthToken(
                appProperties.getAuth().getTokenSecret(),
                new Date(now.getTime() + refreshTokenExpiry)
        );

        return new AuthTokens(refreshToken, accessToken);
    }
}
