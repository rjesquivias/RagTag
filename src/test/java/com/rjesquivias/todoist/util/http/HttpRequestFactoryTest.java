package com.rjesquivias.todoist.util.http;

import static com.rjesquivias.todoist.util.http.HttpRequestHelperTest.buildTestProject;
import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import java.net.http.HttpRequest;
import org.junit.Test;
import org.mockito.Mockito;

public class HttpRequestFactoryTest {

  private final String invalidUriString = "http://finance.yahoo.com/q/h?s=^IXIC";
  private final String validUriString = "http://finance.yahoo.com/";

  private final String testToken = "test-token";

  @Test
  public void whenBuildPost_givenInvalidUri_throwsRuntimeException() {
    HttpRequestFactory sut = new HttpRequestFactory(testToken, new ObjectMapper());
    assertThrows(RuntimeException.class,
        () -> sut.buildPost(invalidUriString, buildTestProject()));
  }

  @Test
  public void whenBuildPost_givenJson_throwsRuntimeException() throws JsonProcessingException {
    ObjectMapper objectMapper = Mockito.spy(new ObjectMapper());
    Mockito.when(objectMapper.writeValueAsString(any()))
        .thenThrow(new JsonProcessingException("") {
        });

    HttpRequestFactory sut = new HttpRequestFactory(testToken, objectMapper);

    assertThrows(RuntimeException.class,
        () -> sut.buildPost(validUriString, buildTestProject()));
  }

  @Test
  public void whenBuildPost_happyPath_constructsAuthorizationHeader() {
    HttpRequestFactory sut = new HttpRequestFactory(testToken, new ObjectMapper());

    HttpRequest request = sut.buildPost(validUriString, buildTestProject());

    assertEquals(ImmutableList.of(format("Bearer %s", testToken)),
        request.headers().map().get("Authorization"));
  }

  @Test
  public void whenBuildPost_happyPath_constructsContentTypeHeader() {
    HttpRequestFactory sut = new HttpRequestFactory(testToken, new ObjectMapper());

    HttpRequest request = sut.buildPost(validUriString, buildTestProject());

    assertEquals(ImmutableList.of("application/json"),
        request.headers().map().get("Content-Type"));
  }

  @Test
  public void whenBuildGet_givenInvalidUri_throwsRuntimeException() {
    HttpRequestFactory sut = new HttpRequestFactory(testToken, new ObjectMapper());
    assertThrows(RuntimeException.class,
        () -> sut.buildGet(invalidUriString));
  }

  @Test
  public void whenBuildGet_happyPath_constructsAuthorizationHeader() {
    HttpRequestFactory sut = new HttpRequestFactory(testToken, new ObjectMapper());

    HttpRequest request = sut.buildGet(validUriString);

    assertEquals(ImmutableList.of(format("Bearer %s", testToken)),
        request.headers().map().get("Authorization"));
  }

  @Test
  public void whenBuildDelete_givenInvalidUri_throwsRuntimeException() {
    HttpRequestFactory sut = new HttpRequestFactory(testToken, new ObjectMapper());
    assertThrows(RuntimeException.class,
        () -> sut.buildDelete(invalidUriString));
  }

  @Test
  public void whenBuildDelete_happyPath_constructsAuthorizationHeader() {
    HttpRequestFactory sut = new HttpRequestFactory(testToken, new ObjectMapper());

    HttpRequest request = sut.buildDelete(validUriString);

    assertEquals(ImmutableList.of(format("Bearer %s", testToken)),
        request.headers().map().get("Authorization"));
  }
}
