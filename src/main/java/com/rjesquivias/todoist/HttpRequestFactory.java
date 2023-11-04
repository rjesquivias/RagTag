package com.rjesquivias.todoist;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;

final class HttpRequestFactory {

    public static final String BEARER = "Bearer ";
    public static final String AUTHORIZATION = "Authorization";
    private final String token;
    private final ObjectMapper objectMapper;


    public HttpRequestFactory(String token, ObjectMapper objectMapper) {
        this.token = token;
        this.objectMapper = objectMapper;
    }

    public HttpRequest buildPost(String uri, Object obj) {
        try {
            String json = objectMapper.writeValueAsString(obj);
            return buildPost(uri, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpRequest buildPost(String uri, String body) {
        try {
            return HttpRequest.newBuilder().uri(new URI(uri))
                    .header(AUTHORIZATION, BEARER + token)
                    .header("Content-Type", "application/json")
                    .POST(BodyPublishers.ofString(body)).build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpRequest buildGet(String uri) {
        try {
            return HttpRequest.newBuilder().uri(new URI(uri))
                    .header(AUTHORIZATION, BEARER + token)
                    .GET().build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpRequest buildDelete(String uri) {
        try {
            return HttpRequest.newBuilder().uri(new URI(uri))
                    .header(AUTHORIZATION, BEARER + token)
                    .DELETE().build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
