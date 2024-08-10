package com.yesolll.comap_api_server.domain.place.controller;

import com.yesolll.comap_api_server.domain.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/place")
@RequiredArgsConstructor
public class PlaceApiController {

    private final PlaceService placeService;

}
