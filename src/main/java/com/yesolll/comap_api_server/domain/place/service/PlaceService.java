package com.yesolll.comap_api_server.domain.place.service;

import com.yesolll.comap_api_server.domain.place.repository.PlaceRepository;
import com.yesolll.comap_api_server.util.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final KakaoService kakaoService;
    private final PlaceRepository placeRepository;

    public String searchPlacesByCategory() {
        return kakaoService.getPlacesByCategory();
    }

}
