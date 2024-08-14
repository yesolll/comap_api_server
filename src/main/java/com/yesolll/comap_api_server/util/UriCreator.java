package com.yesolll.comap_api_server.util;

import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class UriCreator<E> {

    public String makeQueryParam(E dto) throws IllegalAccessException {

        StringBuilder uriBuilder = new StringBuilder("?");

        for (Field field : dto.getClass().getDeclaredFields()){
            field.setAccessible(true);
            Object value = field.get(dto);
            if (value != null) {
                uriBuilder
                        .append(CaseConverter.camelToSnake(field.getName()))
                        .append("=")
                        .append(URLEncoder.encode(value.toString(), StandardCharsets.UTF_8))
                        .append("&");
            }
        }

        return uriBuilder.toString();
    }

}
