package com.yesolll.comap_api_server.common.dto.mail;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MailDto {
//    private String[] address; // TODO: 단체메일
    private String address;
    private String title;
    private String authKey;
    private String template;
}