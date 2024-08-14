package com.yesolll.comap_api_server.domain.place.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class KakaoApiResponseDto {
    private MetaInfo meta;
    private ArrayList<Place> documents;

    @Data
    @AllArgsConstructor
    static class MetaInfo {
        private int totalCount;
        private int pageableCount;
        private boolean isEnd;
        private SameName sameName;
    }

    @Data
    @AllArgsConstructor
    static class SameName {
        private String[] region;
        private String keyword;
        private String selectedRegion;
    }

    @Data
    @AllArgsConstructor
    static class Place {
        private String id;
        private String placeName;
        private String categoryName;
        private String categoryGroupCode;
        private String categoryGroupName;
        private String phone;
        private String addressName;
        private String roadAddressName;
        private String x;
        private String y;
        private String placeUrl;
        private String distance;
    }

}