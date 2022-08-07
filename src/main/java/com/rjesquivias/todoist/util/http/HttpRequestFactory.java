package com.rjesquivias.todoist.util.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;

public class HttpRequestFactory {

  private final String token;
  private final ObjectMapper objectMapper;


  public HttpRequestFactory(String token, ObjectMapper objectMapper) {
    this.token = token;
    this.objectMapper = objectMapper;
  }

  public HttpRequest buildPost(String uri, Object obj) {
    try {
      return HttpRequest.newBuilder().uri(new URI(uri))
          .header("Authorization", "Bearer " + token)
          .header("Content-Type", "application/json")
          .POST(BodyPublishers.ofString(objectMapper.writeValueAsString(obj))).build();
    } catch (URISyntaxException | JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public HttpRequest buildGet(String uri) {
    try {
      return HttpRequest.newBuilder().uri(new URI(uri))
          .header("Authorization", "Bearer " + token)
          .GET().build();
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  public HttpRequest buildDelete(String uri) {
    try {
      return HttpRequest.newBuilder().uri(new URI(uri))
          .header("Authorization", "Bearer " + token)
          .DELETE().build();
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }
}
