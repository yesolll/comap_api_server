package com.yesolll.comap_api_server.domain.member.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    private String nickname;
    private String password;
}
