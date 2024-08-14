package com.yesolll.comap_api_server.domain.member.service;

import com.yesolll.comap_api_server.common.dto.mail.MailDto;
import com.yesolll.comap_api_server.domain.member.entity.Member;
import com.yesolll.comap_api_server.domain.member.repository.MemberRepository;
import com.yesolll.comap_api_server.util.service.mailSender.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
//    private final MailService mailService;

    @Transactional
    public Long saveMember(Member member) {
        Member newMember = memberRepository.save(member);
        return newMember.getId();
    }

//    public void sendAuthenticationMail(String email) throws MessagingException {
//        MailDto mailDto = new MailDto();
//        mailDto.setAddress(new String[]{email});
//        mailDto.setTitle("회원가입 인증 메일");
//        mailDto.setAuthId("asdfasdf");
//        mailDto.setTemplate("joinMailTemplate");
//
//        mailService.sendTemplateMessage(mailDto);
//
//
//    }

}
