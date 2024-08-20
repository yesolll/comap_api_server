package com.yesolll.comap_api_server.domain.member.service;

import com.yesolll.comap_api_server.domain.member.entity.Member;
import com.yesolll.comap_api_server.domain.member.entity.MemberToken;
import com.yesolll.comap_api_server.domain.member.repository.MemberRepository;
import com.yesolll.comap_api_server.domain.member.repository.MemberTokenRepository;
import com.yesolll.comap_api_server.domain.member.security.util.JWTTokenizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberTokenService {

    private final MemberTokenRepository memberTokenRepository;
    private final JWTTokenizer tokenizer;
    private final MemberRepository memberRepository;

    @Transactional
    public Map<String, String> reissueAccessToken(String refreshToken) {
        MemberToken memberToken = findValidToken(refreshToken);
        Long memberId = memberToken.getMemberId();
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("없는 회원"));

        Map<String, String> response = new HashMap<>();
        if (isVerifiedToken(memberToken)) {
            response.put("accessToken", delegateAccessToken(member));
            response.put("refreshToken", delegateRefreshToken(member));
            return response;
        }
        throw new RuntimeException("만료된 토큰");
    }

    @Transactional
    public void changeTokenValid(Long memberId) {
        MemberToken memberToken = memberTokenRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("유효하지 않은 토큰"));
        memberTokenRepository.delete(memberToken);
    }

    public MemberToken findValidToken(String refreshToken) {
        return memberTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("유효하지 않은 토큰"));
    }

    public boolean isVerifiedToken(MemberToken memberToken) {
        return memberToken.getExpirationDate().after(new Date());
    }

    public String delegateAccessToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", member.getId());
        String subject = member.getNickname();
        Date expirationDate = tokenizer.getTokenExpirationDate(tokenizer.getAccessTokenExpirationMinutes());
        String base64EncodedSecretKey = tokenizer.encodeBase64SecretKey(tokenizer.getSecretKey());

        return tokenizer.generateAccessToken(claims, subject, expirationDate, base64EncodedSecretKey);
    }

    private String delegateRefreshToken(Member member) {
        String subject = member.getNickname();
        String base64EncodedSecretKey = tokenizer.encodeBase64SecretKey(tokenizer.getSecretKey());
        Date expirationDate = tokenizer.getTokenExpirationDate(tokenizer.getRefreshTokenExpirationMinutes());
        String refreshToken = tokenizer.generateRefreshToken(subject, expirationDate, base64EncodedSecretKey);

        saveRefreshToken(member, expirationDate, refreshToken);
        return refreshToken;
    }

    public void saveRefreshToken(Member member, Date expirationDate, String refreshToken) {
        MemberToken memberToken = memberTokenRepository.findByMemberId(member.getId()).orElse(new MemberToken());
        memberToken.refresh(refreshToken, expirationDate);
        memberTokenRepository.save(memberToken);
    }

}