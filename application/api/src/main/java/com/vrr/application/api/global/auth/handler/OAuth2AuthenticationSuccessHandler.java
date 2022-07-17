package com.vrr.application.api.global.auth.handler;

import com.vrr.application.api.global.auth.properties.AppProperties;
import com.vrr.application.api.global.auth.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import com.vrr.application.api.global.auth.token.AuthToken;
import com.vrr.application.api.global.auth.token.AuthTokenProvider;
import com.vrr.common.auth.info.OAuth2UserInfo;
import com.vrr.common.auth.info.OAuth2UserInfoFactory;
import com.vrr.common.code.auth.ProviderType;
import com.vrr.common.code.auth.RoleType;
import com.vrr.common.auth.utils.CookieUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AuthTokenProvider tokenProvider;
    private final AppProperties appProperties;
    private final HttpCookieOAuth2AuthorizationRequestRepository authorizationRequestRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookie(request, HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new IllegalArgumentException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", getAccessTokenAndSetRefreshToken(request, response, authentication))
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);

        return appProperties.getOauth2().getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    // Only validate host and port. Let the clients use different paths if they want to
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    if (authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort()) {
                        return true;
                    }
                    return false;
                });
    }

    private String getAccessTokenAndSetRefreshToken(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Date now = new Date();

        // access 토큰 생성
        AuthToken accessToken = tokenProvider.createAuthToken(
                getOAuth2UserInfo(authentication).getId(),
                getRoleType(authentication).getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        // refresh 토큰 생성
        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
        AuthToken refreshToken = tokenProvider.createAuthToken(
                appProperties.getAuth().getTokenSecret(),
                new Date(now.getTime() + refreshTokenExpiry)
        );

        int cookieMaxAge = (int) refreshTokenExpiry / 60;
        CookieUtils.deleteCookie(request, response, HttpCookieOAuth2AuthorizationRequestRepository.REFRESH_TOKEN);
        CookieUtils.addCookie(response, HttpCookieOAuth2AuthorizationRequestRepository.REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);
        return accessToken.getToken();
    }

    private OAuth2UserInfo getOAuth2UserInfo(Authentication authentication) {
        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        ProviderType providerType = ProviderType.valueOf(authToken.getAuthorizedClientRegistrationId().toUpperCase());
        OAuth2User user = (OAuth2User) authentication.getPrincipal();
        return OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());
    }

    private RoleType getRoleType(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = ((OAuth2User) authentication.getPrincipal()).getAuthorities();
        return hasAuthority(authorities, RoleType.ADMIN.getCode()) ? RoleType.ADMIN : RoleType.USER;
    }

    private boolean hasAuthority(Collection<? extends GrantedAuthority> authorities, String authority) {
        if (authorities == null) {
            return false;
        }

        for (GrantedAuthority grantedAuthority : authorities) {
            if (authority.equals(grantedAuthority.getAuthority())) {
                return true;
            }
        }
        return false;
    }
}
