package com.yesolll.comap_api_server.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class SingleResponseDto<T> {
    @Setter
    private T response;
    private String message;
    private int httpCode;

    @Builder
    public SingleResponseDto(T response, String message, int httpCode) {
        this.response = response;
        this.message = message;
        this.httpCode = httpCode;
    }

    @Builder
    public SingleResponseDto(String message, int httpCode) {
        this.message = message;
        this.httpCode = httpCode;
    }
}
