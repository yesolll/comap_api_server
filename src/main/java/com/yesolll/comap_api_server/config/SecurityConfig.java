package com.yesolll.comap_api_server.config;

import com.yesolll.comap_api_server.domain.member.security.filter.JwtAuthenticationFilter;
import com.yesolll.comap_api_server.domain.member.security.handler.MemberAccessDeniedHandler;
import com.yesolll.comap_api_server.domain.member.security.handler.MemberAuthenticationEntryPoint;
import com.yesolll.comap_api_server.domain.member.security.handler.MemberAuthenticationFailureHandler;
import com.yesolll.comap_api_server.domain.member.security.handler.MemberAuthenticationSuccessHandler;
import com.yesolll.comap_api_server.domain.member.security.util.JWTTokenizer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTTokenizer jwtTokenizer;
//    private final CustomAuthorityUtils authorityUtils;
    private final MemberAuthenticationSuccessHandler authenticationSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder sharedObject = http.getSharedObject(AuthenticationManagerBuilder.class);
        AuthenticationManager authenticationManager = sharedObject.build();

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/jwt/login");
        // SuccessHandler, FailureHandler 각각 구현 클래스 생성 -> DI가 아닌 new도 무방하다
        jwtAuthenticationFilter.setAuthenticationSuccessHandler(
                authenticationSuccessHandler);
        jwtAuthenticationFilter.setAuthenticationFailureHandler(
                new MemberAuthenticationFailureHandler());

        http    .authenticationManager(authenticationManager)
                .headers(((headerConfig)->
                        headerConfig.frameOptions((HeadersConfigurer.FrameOptionsConfig::sameOrigin))))
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .httpBasic(HttpBasicConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(eh -> {
                    eh.authenticationEntryPoint(new MemberAuthenticationEntryPoint());
                    eh.accessDeniedHandler(new MemberAccessDeniedHandler());
                })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll()
                )
//                .antMatchers("/api/**").permitAll()
//                .authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
//                .anyRequest().permitAll() // 우선 모든 요청 허용, 제한은 통합 후에 설정 !
//                    .mvcMatchers(HttpMethod.POST,
//                            "/members",
//                            "/auth/refresh"
//                    )
//                    .permitAll()
//                    .mvcMatchers(HttpMethod.GET,
//                            "/members/profile/**",
//                            "/members/questions/**",
//                            "/members/answers/**",
//                            "/questions/**",
//                            "/answers/**"
//                    ).permitAll()
//                    .anyRequest().authenticated()
//            )
        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder createPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // Custom Filters 등록
//    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
//        @Override
//        public void configure(HttpSecurity builder) throws Exception {
//            // SecurityConfigurer 간 공유객체 중 AuthenticationManager 할당
//            AuthenticationManager authenticationManager
//                    = builder.getSharedObject(AuthenticationManager.class);
//
//            // -- authenticationFilter
//            JwtAuthenticationFilter jwtAuthenticationFilter
//                    = new JwtAuthenticationFilter(authenticationManager);
//
//            jwtAuthenticationFilter.setFilterProcessesUrl("/jwt/login");
//            // SuccessHandler, FailureHandler 각각 구현 클래스 생성 -> DI가 아닌 new도 무방하다
//            jwtAuthenticationFilter.setAuthenticationSuccessHandler(
//                    authenticationSuccessHandler);
//            jwtAuthenticationFilter.setAuthenticationFailureHandler(
//                    new MemberAuthenticationFailureHandler());
//
//            // -- verificationFilter
//            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer);
//
//            builder.addFilter(new CorsConfig().corsFilter())
//                    .addFilter(jwtAuthenticationFilter)
//                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
//
//        }
//    }



}
