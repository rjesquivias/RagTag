package com.rjesquivias.todoist.util.http;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rjesquivias.todoist.exceptions.ServiceUnavailableException;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

public class HttpRequestHelper {

  private final HttpClient client;
  private final ObjectMapper objectMapper;

  public HttpRequestHelper(HttpClient client) {
    this.client = client;
    this.objectMapper = new ObjectMapper();
  }

  public <T> T makeCollectionRequest(HttpRequest request, ResponsePredicate predicate,
      Class clazz) {
    JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
    return request(request, predicate, type);
  }

  public <T> T makeRequest(HttpRequest request, ResponsePredicate predicate,
      Class clazz) {
    JavaType type = objectMapper.getTypeFactory().constructType(clazz);
    return request(request, predicate, type);
  }

  <T> T request(HttpRequest request, ResponsePredicate predicate, JavaType type) {
    try {
      HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
      if (predicate.isValid(response)) {
        return !response.body().isEmpty() ? objectMapper.readValue(response.body(), type) : null;
      } else {
        throw new ServiceUnavailableException(
            String.format("Status: %d Body: %s", response.statusCode(), response.body()));
      }
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
