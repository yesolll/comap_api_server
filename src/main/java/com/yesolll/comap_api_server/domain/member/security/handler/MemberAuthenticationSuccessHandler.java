package com.yesolll.comap_api_server.domain.member.security.handler;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.yesolll.comap_api_server.common.dto.SingleResponseDto;
import com.yesolll.comap_api_server.common.enums.ResultCode;
import com.yesolll.comap_api_server.domain.member.entity.Member;
import com.yesolll.comap_api_server.domain.member.security.dto.LoginResponseDto;
import com.yesolll.comap_api_server.domain.member.security.util.JWTTokenizer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/*
 * 인증 성공 핸들러
 */
@Slf4j
@Component
public class MemberAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

    private final JWTTokenizer tokenizer;
    private final TokenRepository tokenRepository;

    public MemberAuthenticationSuccessHandler(JWTTokenizer tokenizer, TokenRepository tokenRepository) {
        this.tokenizer = tokenizer;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        sendSuccessResponse(response, authentication);
    }

    private void sendSuccessResponse(HttpServletResponse response, Authentication authentication) throws IOException {
        Member member = (Member) authentication.getPrincipal();

        String accessToken = delegateAccessToken(member);
        String refreshToken = delegateRefreshToken(member);

        Gson gson = new Gson();
        LoginResponseDto memberInfo = new LoginResponseDto(member.getId(), member.getNickname());
        SingleResponseDto<LoginResponseDto> responseDto
                = new SingleResponseDto<LoginResponseDto>(
                        memberInfo,
                        ResultCode.CREATE_COMPLETED.getMessage(),
                        ResultCode.CREATE_COMPLETED.getHttpCode());

        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("RefreshToken", refreshToken);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(gson.toJson(responseDto, SingleResponseDto.class));
    }


    // Access Token 발급
    private String delegateAccessToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", member.getId());
        claims.put("nickname", member.getNickname());
        String subject = member.getNickname();
        Date expirationDate = tokenizer.getTokenExpirationDate(tokenizer.getAccessTokenExpirationMinutes());
        String base64EncodedSecretKey = tokenizer.encodeBase64SecretKey(tokenizer.getSecretKey());

        return tokenizer.generateAccessToken(claims, subject, expirationDate, base64EncodedSecretKey);
    }

    // Refresh Token 발급
    private String delegateRefreshToken(Member member) {
        String subject = member.getNickname();
        Date expirationDate = tokenizer.getTokenExpirationDate(tokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = tokenizer.encodeBase64SecretKey(tokenizer.getSecretKey());
        String refreshToken = tokenizer.generateRefreshToken(subject, expirationDate, base64EncodedSecretKey);

        saveRefreshToken(member, refreshToken, expirationDate);
        return refreshToken;
    }

    public void saveRefreshToken(Member member, String refreshToken, Date expirationDate) {
        Token token = tokenRepository.findByMemberId(member.getId()).orElse(new Token());
        token.setMember(member);
        token.setRefreshToken(refreshToken);
        token.setExpirationDate(expirationDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());
        tokenRepository.save(token);
    }
}