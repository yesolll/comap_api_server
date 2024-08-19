package com.yesolll.comap_api_server.domain.member.service;

import com.yesolll.comap_api_server.common.dto.mail.MailDto;
import com.yesolll.comap_api_server.domain.member.entity.MemberAuth;
import com.yesolll.comap_api_server.domain.member.repository.MemberAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberAuthService {

    private final MemberAuthRepository memberAuthRepository;

    public void saveMemberAuth(MailDto mailDto) {
        MemberAuth memberAuth = MemberAuth.builder()
                .email(mailDto.getAddress())
                .memberId(Long.valueOf(mailDto.getAuthKey().split("-")[0]))
                .authKey(mailDto.getAuthKey().split("-")[1])
                .expireDate(LocalDateTime.now().plusHours(1L))
                .authType("JOIN_WAITING")
                .build();
        memberAuthRepository.save(memberAuth);
    }

    public Long updateMemberAuth(String authKey, String authType) {
        MemberAuth auth = memberAuthRepository.findMemberAuthByMemberIdAndAuthKeyAndAuthType(
                Long.valueOf(authKey.split("-")[0]),
                authKey.split("-")[1],
                authType
        ).orElseThrow(() -> new RuntimeException("인증 정보 없음"));

        auth.updateAuthTypeToComplete(authType);
        memberAuthRepository.save(auth);
        return auth.getMemberId();
    }

}
