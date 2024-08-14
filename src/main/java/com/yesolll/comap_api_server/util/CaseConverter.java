package com.yesolll.comap_api_server.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CaseConverter {

    public static String camelToSnake(String camel) {
        Pattern pattern = Pattern.compile("([a-z])([A-Z])");
        Matcher matcher = pattern.matcher(camel);

        String snake = matcher.replaceAll(matchResult -> {
            return String.format("%s_%s", matchResult.group(1), matchResult.group(2).toLowerCase());
        });

        return snake;
    }

    public static String snakeToCamel(String snake) {
        Pattern pattern = Pattern.compile("([a-z])(_)([a-z])");
        Matcher matcher = pattern.matcher(snake);

        String camel = matcher.replaceAll(matchResult -> {
            return String.format("%s%s", matchResult.group(1), matchResult.group(3).toUpperCase());
        });

        return camel;
    }

}
