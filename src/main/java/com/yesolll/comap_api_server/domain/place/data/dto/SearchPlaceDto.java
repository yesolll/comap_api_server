package com.yesolll.comap_api_server.domain.place.data.dto;

import lombok.*;

@Getter
@Setter
public class SearchPlaceDto {

    private String categoryGroupCode; // TODO enum
    private String x;
    private String y;
    private String radius;
    private String rect;
    private String page;
    private String size;
    private String sort;

}
