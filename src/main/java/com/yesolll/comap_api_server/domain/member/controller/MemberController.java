package com.yesolll.comap_api_server.domain.member.controller;

import com.yesolll.comap_api_server.common.dto.SingleResponseDto;
import com.yesolll.comap_api_server.domain.member.data.dto.MemberJoinDto;
import com.yesolll.comap_api_server.domain.member.mapper.MemberMapper;
import com.yesolll.comap_api_server.domain.member.service.MemberAuthService;
import com.yesolll.comap_api_server.domain.member.service.MemberService;
import com.yesolll.comap_api_server.util.service.mailSender.MailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberMapper memberMapper;
    private final MemberService memberService;

    private final MailService mailService;
    private final MemberAuthService memberAuthService;

    @PostMapping("/join")
    public ResponseEntity join(@Valid @RequestBody MemberJoinDto memberJoinDto) {
        try {
            Long newMemberId = memberService.createMember(
                    memberMapper.from(memberJoinDto), memberJoinDto.getEmail()
            );

            SingleResponseDto responseDto = SingleResponseDto.builder().build();
            return ResponseEntity.created(URI.create(newMemberId.toString())).body(responseDto); // TODO: Create Response URI
        } catch (Exception e) {
            log.info("TODO: 메일 보내기 예외처리");
            return ResponseEntity.internalServerError().build();
        }

    }

    @GetMapping("/authentication") // TODO 화면 구현 & PostMapping
    public ResponseEntity processAuthentication(@RequestParam String authKey) {
        log.info("{}", authKey);
        Long memberId = memberAuthService.updateMemberAuth(authKey, "JOIN_WAITING"); // TODO ENUM
        memberService.updateMemberStatus(memberId, "JOIN_WAITING");
        return ResponseEntity.ok().build();
    }

}
