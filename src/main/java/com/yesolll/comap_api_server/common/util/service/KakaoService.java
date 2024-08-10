package com.yesolll.comap_api_server.common.util.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
@RequiredArgsConstructor
public class KakaoService {

    private final HttpSession httpSession;

    @Value("${kakao.rest-api-host}")
    private String REST_API_HOST;

    @Value("${kakao.rest-api-key}")
    private String REST_API_KEY;


}
