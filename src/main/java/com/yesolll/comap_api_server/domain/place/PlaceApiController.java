package com.yesolll.comap_api_server.domain.place;

import com.yesolll.comap_api_server.util.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/place")
@RequiredArgsConstructor
public class PlaceApiController {

    private final KakaoService kakaoService;

    /**
     * 카테고리로 장소 검색하기
     */
    @GetMapping(value = "/search")
    public void searchPlaces() {
        String result = kakaoService.getPlacesByCategory();
        System.out.println(result);
    }



}
