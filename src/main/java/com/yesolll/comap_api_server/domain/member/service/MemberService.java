package com.yesolll.comap_api_server.domain.member.service;

import com.yesolll.comap_api_server.domain.member.entity.Member;
import com.yesolll.comap_api_server.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long saveMember(Member member) {
        Member newMember = memberRepository.save(member);
        return newMember.getId();
    }

}
