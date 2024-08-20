package com.yesolll.comap_api_server.domain.place.controller;

import com.yesolll.comap_api_server.domain.place.data.dto.KakaoApiResponseDto;
import com.yesolll.comap_api_server.domain.place.data.dto.SearchPlaceDto;
import com.yesolll.comap_api_server.domain.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/place")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", methods = RequestMethod.GET)
public class PlaceApiController {

    private final PlaceService placeService;

    /**
     * 카테고리로 장소 검색하기
     */
    @GetMapping(value = "/search")
    public ResponseEntity searchPlaces(SearchPlaceDto searchPlaceDto) {

        try {
            KakaoApiResponseDto result = placeService.searchPlacesByCategory(searchPlaceDto);
            return ResponseEntity.ok(result);
        } catch(Exception e){ // TODO
            return ResponseEntity.internalServerError().build();
        }
    }

}
