package com.yesolll.comap_api_server.domain.member.controller;

import com.yesolll.comap_api_server.common.dto.SingleResponseDto;
import com.yesolll.comap_api_server.common.dto.mail.MailDto;
import com.yesolll.comap_api_server.domain.member.data.dto.MemberJoinDto;
import com.yesolll.comap_api_server.domain.member.mapper.MemberMapper;
import com.yesolll.comap_api_server.domain.member.service.MemberService;
import com.yesolll.comap_api_server.util.service.mailSender.MailService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberMapper memberMapper;
    private final MemberService memberService;

    private final MailService mailService;

    @PostMapping("/test")
    public void join() throws MessagingException {
        MailDto mailDto = new MailDto();
        mailDto.setAddress(new String[]{"ddorimeos@gmail.com"});
        mailDto.setTitle("회원가입 인증 메일");
        mailDto.setContent("인증번호 입력하세요: 123456");
        mailDto.setTemplate("");

        mailService.sendTemplateMessage(mailDto);
    }

    @PostMapping("/join")
    public ResponseEntity join(@Valid @RequestBody MemberJoinDto memberJoinDto) {
         Long newMemberId = memberService.saveMember(memberMapper.from(memberJoinDto));

        SingleResponseDto responseDto = SingleResponseDto.builder().build();
        return ResponseEntity.created(URI.create(newMemberId.toString())).body(responseDto); // TODO: Create Response URI
    }

}
