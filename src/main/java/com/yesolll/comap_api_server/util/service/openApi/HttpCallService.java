package com.yesolll.comap_api_server.util.service.openApi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Slf4j
@Service
public class HttpCallService {

    public String Call(String method, String reqURL, String header, String param) {
        StringBuilder result = new StringBuilder();
        try {
            String response = "";
            if(param != null) reqURL += param;

            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Authorization", header);

            log.info("{}", String.format("Method: %s | RequestURL: %s \n", method, reqURL));

            InputStream stream = conn.getErrorStream();
            if (stream != null) {
                try (Scanner scanner = new Scanner(stream)) {
                    scanner.useDelimiter("\\Z");
                    response = scanner.next();
                }
                log.info("{}", String.format("Error response: %s \n", response));
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            while ((line = br.readLine()) != null) {
                result.append(line);
            }

            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result.toString();
    }

    public String CallwithToken(String method, String reqURL, String access_Token) {
        return CallwithToken(method, reqURL, access_Token, null);
    }

    public String CallwithToken(String method, String reqURL, String access_Token, String param) {
        String header = "KakaoAK " + access_Token;
        return Call(method, reqURL, header, param);
    }
}