package com.yesolll.comap_api_server.util.service.openApi;

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

    public String getPlacesByCategory(String param) {
        String uri = KAKAO_API_HOST + "/v2/local/search/category";
        return httpCallService.CallwithToken(
                HttpMethod.GET.name(),
                uri,
                REST_API_KEY,
                param
        );
    }

    public String getPlacesByKeyword(String param) {
        String uri = KAKAO_API_HOST + "/v2/local/search/keyword";
        return httpCallService.CallwithToken(
                HttpMethod.GET.name(),
                uri,
                REST_API_KEY,
                param
        );
    }

}