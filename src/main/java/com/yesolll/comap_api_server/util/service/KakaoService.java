package com.yesolll.comap_api_server.util.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoService {

    private final HttpCallService httpCallService;

    @Value("${kakao.rest-api-key}")
    private String REST_API_KEY;

    @Value("${kakao.rest-api-host}")
    private String KAKAO_API_HOST;

    public String getPlacesByCategory() {
        String uri = KAKAO_API_HOST + "/v2/local/search/category.json";
        return httpCallService.CallwithToken(
                HttpMethod.GET.name(),
                uri,
                REST_API_KEY,
                "?category_group_code=FD6" // TODO
        );
    }

}