package com.yesolll.comap_api_server.domain.member.repository;

import com.yesolll.comap_api_server.domain.member.entity.MemberAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberAuthRepository extends JpaRepository<MemberAuth, Long> {
    Optional<MemberAuth> findMemberAuthByMemberIdAndAuthKeyAndAuthType(Long memberId, String authKey, String authType);
}

