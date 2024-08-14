package com.yesolll.comap_api_server.domain.place.service;

import com.google.gson.Gson;
import com.yesolll.comap_api_server.domain.place.data.dto.KakaoApiResponseDto;
import com.yesolll.comap_api_server.domain.place.data.dto.SearchPlaceDto;
import com.yesolll.comap_api_server.domain.place.repository.PlaceRepository;
import com.yesolll.comap_api_server.util.CaseConverter;
import com.yesolll.comap_api_server.util.service.openApi.KakaoService;
import com.yesolll.comap_api_server.util.UriCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final UriCreator uriCreator;
    private final KakaoService kakaoService;
    private final PlaceRepository placeRepository;

    public KakaoApiResponseDto searchPlacesByCategory(SearchPlaceDto searchPlaceDto) throws IllegalAccessException {
        String queryParam = uriCreator.makeQueryParam(searchPlaceDto);
        String kakaoResponse = CaseConverter.snakeToCamel(kakaoService.getPlacesByCategory(queryParam));

        Gson gson = new Gson();
        KakaoApiResponseDto responseDto = gson.fromJson(kakaoResponse, KakaoApiResponseDto.class);
        return responseDto;
    }

}
