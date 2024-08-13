package com.yesolll.comap_api_server.domain.member.controller;

import com.yesolll.comap_api_server.common.dto.SingleResponseDto;
import com.yesolll.comap_api_server.domain.member.data.dto.MemberJoinDto;
import com.yesolll.comap_api_server.domain.member.mapper.MemberMapper;
import com.yesolll.comap_api_server.domain.member.service.MemberService;
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

    @PostMapping("/join")
    public ResponseEntity join(@Valid @RequestBody MemberJoinDto memberJoinDto) {
         Long newMemberId = memberService.saveMember(memberMapper.from(memberJoinDto));

        SingleResponseDto responseDto = SingleResponseDto.builder().build();
        return ResponseEntity.created(URI.create(newMemberId.toString())).body(responseDto); // TODO: Create Response URI
    }

}
