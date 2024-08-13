package com.yesolll.comap_api_server.domain.member.data.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class MemberJoinDto {
    
    @NotBlank(message = "이메일은 필수 입력입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "닉네임은 필수 입력입니다.")
    private String nickname;

    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    @Pattern(regexp = "^.*(?=^.{4,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$",
             message = "비밀번호는 4~15자리의 숫자,문자,특수문자로 이루어져야합니다.")
    private String password;

}
