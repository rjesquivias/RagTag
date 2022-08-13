package com.rjesquivias.todoist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

public class HttpRequestHelperTest {

  private final HttpClient mockedHttpClient = Mockito.mock(HttpClient.class);
  private final HttpRequestFactory httpRequestFactory = new HttpRequestFactory("test-token",
      new ObjectMapper());
  private final ObjectMapper objectMapper = new ObjectMapper();

  private final String testUri = "https://test-uri.com";

  @Test
  public void whenRequest_throwsIoException_throwsRuntimeException()
      throws IOException, InterruptedException {

    when(mockedHttpClient.send(any(), any())).thenThrow(new IOException());
    HttpRequestHelper sut = HttpRequestHelper.build(mockedHttpClient);
    HttpRequest request = httpRequestFactory.buildGet(testUri);
    JavaType type = objectMapper.getTypeFactory().constructType(String.class);

    assertThrows(RuntimeException.class,
        () -> sut.request(request, (r) -> true, type));
  }

  @Test
  public void whenRequest_throwsInterruptedException_throwsRuntimeException()
      throws IOException, InterruptedException {

    when(mockedHttpClient.send(any(), any())).thenThrow(new InterruptedException());
    HttpRequestHelper sut = HttpRequestHelper.build(mockedHttpClient);
    HttpRequest request = httpRequestFactory.buildGet(testUri);
    JavaType type = objectMapper.getTypeFactory().constructType(String.class);

    assertThrows(RuntimeException.class,
        () -> sut.request(request, (r) -> true, type));
  }

  @Test
  public void whenRequest_invalidPredicate_throwsServiceUnavailableException()
      throws IOException, InterruptedException {
    @SuppressWarnings("unchecked")
    HttpResponse<String> mockedResponse = Mockito.mock(HttpResponse.class);
    doReturn(mockedResponse).when(mockedHttpClient).send(any(), any());

    HttpRequestHelper sut = HttpRequestHelper.build(mockedHttpClient);
    HttpRequest request = httpRequestFactory.buildGet(testUri);
    JavaType type = objectMapper.getTypeFactory().constructType(String.class);

    assertThrows(ServiceUnavailableException.class,
        () -> sut.request(request, (r) -> false, type));
  }

  @Test
  public void whenRequest_emptyResponseBody_doesNotDeserializeAndReturnsNull()
      throws IOException, InterruptedException {
    @SuppressWarnings("unchecked")
    HttpResponse<String> mockedResponse = Mockito.mock(HttpResponse.class);
    when(mockedResponse.body()).thenReturn("");
    doReturn(mockedResponse).when(mockedHttpClient).send(any(), any());

    HttpRequestHelper sut = HttpRequestHelper.build(mockedHttpClient);
    HttpRequest request = httpRequestFactory.buildGet(testUri);
    JavaType type = objectMapper.getTypeFactory().constructType(String.class);

    Object o = sut.request(request, (r) -> true, type);

    assertNull(o);
  }

  @Test
  public void whenRequest_happyPath_succeeds()
      throws IOException, InterruptedException {
    Project testProject = buildTestProject();
    System.out.println(testProject.id());

    @SuppressWarnings("unchecked")
    HttpResponse<String> mockedResponse = Mockito.mock(HttpResponse.class);
    when(mockedResponse.body()).thenReturn(objectMapper.writeValueAsString(testProject));
    doReturn(mockedResponse).when(mockedHttpClient).send(any(), any());

    HttpRequestHelper sut = HttpRequestHelper.build(mockedHttpClient);
    HttpRequest request = httpRequestFactory.buildGet(testUri);

    Project response = sut.makeRequest(request, (r) -> true, ImmutableProject.class);

    assertEquals(testProject, response);
  }

  @Test
  public void whenMakeCollectionRequest_happyPath_succeeds()
      throws IOException, InterruptedException {
    Collection<Project> testProjects = List.of(buildTestProject(), buildTestProject());

    @SuppressWarnings("unchecked")
    HttpResponse<String> mockedResponse = Mockito.mock(HttpResponse.class);
    when(mockedResponse.body()).thenReturn(objectMapper.writeValueAsString(testProjects));
    doReturn(mockedResponse).when(mockedHttpClient).send(any(), any());

    HttpRequestHelper sut = HttpRequestHelper.build(mockedHttpClient);
    HttpRequest request = httpRequestFactory.buildGet(testUri);

    Collection<Project> response = sut.makeCollectionRequest(request, (r) -> true, ImmutableProject.class);

    assertEquals(testProjects, response);
  }

  @Test
  public void whenMakeRequest_happyPath_succeeds()
      throws IOException, InterruptedException {
    Project testProject = buildTestProject();

    @SuppressWarnings("unchecked")
    HttpResponse<String> mockedResponse = Mockito.mock(HttpResponse.class);
    when(mockedResponse.body()).thenReturn(objectMapper.writeValueAsString(testProject));
    doReturn(mockedResponse).when(mockedHttpClient).send(any(), any());

    HttpRequestHelper sut = HttpRequestHelper.build(mockedHttpClient);
    HttpRequest request = httpRequestFactory.buildGet(testUri);

    Project response = sut.makeRequest(request, (r) -> true, ImmutableProject.class);

    assertEquals(testProject, response);
  }

  static Project buildTestProject() {
    return Project.builder().id(1).name("name").color(Color.BLUE).parentId(1).order(1)
        .commentCount(1).shared(true).favorite(true).inboxProject(true).teamInbox(true)
        .syncId(1).url("url").build();
  }
}
