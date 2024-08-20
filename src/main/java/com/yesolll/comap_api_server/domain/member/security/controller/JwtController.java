package com.yesolll.comap_api_server.domain.member.security.controller;

import com.yesolll.comap_api_server.domain.member.security.dto.LogoutDto;
import com.yesolll.comap_api_server.domain.member.security.dto.TokenRefreshDto;
import com.yesolll.comap_api_server.domain.member.service.MemberTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/jwt")
@RequiredArgsConstructor
public class JwtController {

    private final MemberTokenService tokenService;

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<HttpStatus> logout(@RequestBody LogoutDto logoutDto) {
        tokenService.changeTokenValid(logoutDto.getMemberId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    // Access Token 재발급
    @PostMapping("/refresh")
    public ResponseEntity<String> reissue(@RequestBody TokenRefreshDto refreshDto) {
        Map<String, String> tokens = tokenService.reissueAccessToken(refreshDto.getRefreshToken());
        return ResponseEntity.ok().header("Authorization", "Bearer " + tokens.get("accessToken"))
                .header("RefreshToken", tokens.get("refreshToken"))
                .body(null);
    }

}