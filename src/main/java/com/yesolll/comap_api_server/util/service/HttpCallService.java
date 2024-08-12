package com.yesolll.comap_api_server.util.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

@Service
public class HttpCallService {

    public String Call(String method, String reqURL, String header, String param) {
        String result = "";
        try {
            String response = "";
            if(param != null) reqURL += param;
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Authorization", header);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            System.out.println("reqURL : " + reqURL);
            System.out.println("method : " + method);
            System.out.println("Authorization : " + header);
            InputStream stream = conn.getErrorStream();
            if (stream != null) {
                try (Scanner scanner = new Scanner(stream)) {
                    scanner.useDelimiter("\\Z");
                    response = scanner.next();
                }
                System.out.println("error response : " + response);
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            br.close();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public String CallwithToken(String method, String reqURL, String access_Token) {
        return CallwithToken(method, reqURL, access_Token, null);
    }

    public String CallwithToken(String method, String reqURL, String access_Token, String param) {
        String header = "KakaoAK " + access_Token;
        return Call(method, reqURL, header, param);
    }
}