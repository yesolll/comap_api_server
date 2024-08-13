package com.yesolll.comap_api_server.domain.member.mapper;

import com.yesolll.comap_api_server.domain.member.data.dto.MemberJoinDto;
import com.yesolll.comap_api_server.domain.member.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    Member from(MemberJoinDto memberJoinDto);


}
