package com.github.qiu121.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.qiu121.common.Result;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * @author to_Geek
 * @version 1.0
 * @date 2023/12/23
 */
public class TokenUtil {
    private static final String PASSWORD = "d3b1294a61a07da9b49b6e22b2cbd7f9";
    private static final String LOGIN_URI = "http://localhost:8080/login/doLogin";
    private static final String TOKEN_FILE_PATH = "token.txt";

    /**
     * 获取token值
     *
     * @param userId
     * @return token值
     */
    public static String getToken(Long userId) {
        HttpClient httpClient = HttpClient.newBuilder().build();
        // String requestBody = "mobile=" + userId.toString() + "&password=" + PASSWORD;
        String requestBody = String.format("mobile=%s&password=%s", userId.toString(), PASSWORD);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(LOGIN_URI))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        System.out.println(httpRequest.toString());
        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            System.out.println(responseBody);

            // 序列化JSON值，获取data属性的token值
            ObjectMapper mapper = new ObjectMapper();
            Result result = mapper.readValue(responseBody, Result.class);
            return (String) result.getData();// Token
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将获取的Token存储到文本文件中，便于后续多用户压测
     *
     * @param token
     * @since 11 NIO
     */
    public static void saveTokenToFile(String token) {
        if (token == null) {
            throw new RuntimeException("传递的Token值为 null");
        }
        try {
            Files.writeString(Path.of(TOKEN_FILE_PATH), token + System.lineSeparator(),
                    StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
