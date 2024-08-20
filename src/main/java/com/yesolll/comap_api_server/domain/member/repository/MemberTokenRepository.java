package com.yesolll.comap_api_server.domain.member.repository;

import com.yesolll.comap_api_server.domain.member.entity.MemberToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MemberTokenRepository extends JpaRepository<MemberToken, Long> {

    Optional<MemberToken> findByMemberId(Long memberId);
    Optional<MemberToken> findByRefreshToken(String refreshToken);
    Optional<MemberToken> findByRefreshTokenAndExpirationDateAfter(String refreshToken, LocalDateTime now);
}