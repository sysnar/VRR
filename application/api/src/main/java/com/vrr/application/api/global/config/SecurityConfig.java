package com.vrr.application.api.global.config;

import com.vrr.common.code.auth.RoleType;
import com.vrr.application.api.global.auth.filter.RestAuthenticationEntryPoint;
import com.vrr.application.api.global.auth.filter.TokenAuthenticationFilter;
import com.vrr.application.api.global.auth.handler.OAuth2AuthenticationFailerHandler;
import com.vrr.application.api.global.auth.handler.OAuth2AuthenticationSuccessHandler;
import com.vrr.application.api.global.auth.handler.TokenAccessDeniedHandler;
import com.vrr.application.api.global.auth.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import com.vrr.application.api.global.auth.service.OAuth2UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;
    private final OAuth2UserServiceImpl oAuth2UserService;
    private final HttpCookieOAuth2AuthorizationRequestRepository cookieOAuth2AuthorizationRequestRepository;
    private final OAuth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailerHandler oAuth2AuthenticationFailerHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.cors()
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .csrf().disable()
                    .formLogin().disable()
                    .httpBasic().disable()
                    .exceptionHandling()
                    .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                    .accessDeniedHandler(tokenAccessDeniedHandler)
                .and()
                    .authorizeRequests()
                    .antMatchers("/api/1/auth/*").permitAll()
                    .antMatchers("/api/**").hasAnyAuthority(RoleType.USER.getCode())
//                    .antMatchers("/api/**/admin/**").hasAnyAuthority(RoleType.ADMIN.getCode())
                    .anyRequest().authenticated()
                .and()
                    .oauth2Login(oauth2 -> oauth2
                            .authorizationEndpoint()
                            .baseUri("/oauth2/authorization")
                            .authorizationRequestRepository(cookieOAuth2AuthorizationRequestRepository)
                        .and()
                            .redirectionEndpoint()
                            .baseUri("/*/oauth2/code/*")
                        .and()
                            .userInfoEndpoint()
                            .userService(oAuth2UserService)
                        .and()
                            .successHandler(oauth2AuthenticationSuccessHandler)
                            .failureHandler(oAuth2AuthenticationFailerHandler))
                    .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
