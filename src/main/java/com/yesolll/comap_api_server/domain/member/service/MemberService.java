package com.yesolll.comap_api_server.domain.member.service;

import com.yesolll.comap_api_server.common.dto.mail.MailDto;
import com.yesolll.comap_api_server.domain.member.entity.Member;
import com.yesolll.comap_api_server.domain.member.repository.MemberRepository;
import com.yesolll.comap_api_server.util.KeyCreator;
import com.yesolll.comap_api_server.util.service.mailSender.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MailService mailService;
    private final MemberAuthService memberAuthService;

    private Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    @Transactional
    public Long createMember(Member member, String email) {
        Member newMember = saveMember(member);
        sendAuthenticationMail(newMember.getId(), email);
        return newMember.getId();
    }

    // TODO: Transaction Test
    public void sendAuthenticationMail(Long memberId, String email) {
        String authKey = memberId.toString()
                .concat("-")
                .concat(KeyCreator.getUuid(8));

        MailDto mailDto = new MailDto();
        mailDto.setAddress(email);
        mailDto.setTitle("회원가입 인증 메일");
        mailDto.setAuthKey(authKey);
        mailDto.setTemplate("joinMailTemplate");
        try {
            mailService.sendTemplateMessage(mailDto);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        memberAuthService.saveMemberAuth(mailDto);
    }


    // TODO: orElseThrow Test
    public void updateMemberStatus(Long memberId, String status) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원 정보 없음"));

        member.updateMemberStatusToComplete(status);
        saveMember(member);
        // TODO: 나머지 같은 회원, 인증 정보 삭제
    }

}
