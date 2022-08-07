package com.rjesquivias.todoist.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.rjesquivias.todoist.dao.IProjectDao.CreateArgs;
import com.rjesquivias.todoist.dao.IProjectDao.UpdateArgs;
import com.rjesquivias.todoist.domain.Colors;
import com.rjesquivias.todoist.domain.Project;
import com.rjesquivias.todoist.exceptions.ServiceUnavailableException;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.Collection;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class ProjectDaoTest {

  private final HttpClient mockedHttpClient = Mockito.mock(HttpClient.class);

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void whenGetAll_happyPath_succeeds() throws IOException, InterruptedException {
    Collection<Project> expected = ImmutableList.of(buildTestProject(), buildTestProject());

    @SuppressWarnings("unchecked")
    HttpResponse<String> mockedResponse = Mockito.mock(HttpResponse.class);
    when(mockedResponse.statusCode()).thenReturn(HttpStatus.SC_OK);
    when(mockedResponse.body()).thenReturn(objectMapper.writeValueAsString(expected));
    doReturn(mockedResponse).when(mockedHttpClient).send(any(), any());

    ProjectDao projectDao = new ProjectDao(mockedHttpClient, Dotenv.load());
    Collection<Project> projects = projectDao.getAll();

    assertEquals(expected, projects);
  }

  @Test
  public void whenGetAll_happyPath_requestIsCreatedCorrectly()
      throws IOException, InterruptedException, URISyntaxException {
    Collection<Project> expected = ImmutableList.of(buildTestProject(), buildTestProject());

    @SuppressWarnings("unchecked")
    HttpResponse<String> mockedResponse = Mockito.mock(HttpResponse.class);
    when(mockedResponse.statusCode()).thenReturn(HttpStatus.SC_OK);
    when(mockedResponse.body()).thenReturn(objectMapper.writeValueAsString(expected));
    doReturn(mockedResponse).when(mockedHttpClient).send(any(), any());

    Dotenv dotenv = Dotenv.load();
    String token = dotenv.get("TODOIST_API_TOKEN");
    String baseUri = dotenv.get("PROJECT_URI");
    assert baseUri != null;
    HttpRequest expectedRequest = HttpRequest.newBuilder().uri(new URI(baseUri))
        .header("Authorization", "Bearer " + token)
        .GET().build();

    ProjectDao projectDao = new ProjectDao(mockedHttpClient, dotenv);
    projectDao.getAll();

    ArgumentCaptor<HttpRequest> argumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    verify(mockedHttpClient).send(argumentCaptor.capture(), any());
    HttpRequest capturedArgument = argumentCaptor.getValue();
    assertEquals(expectedRequest, capturedArgument);
  }

  @Test
  public void whenGetAll_responseStatusInvalid_throwsServiceUnavailableException()
      throws IOException, InterruptedException {
    Collection<Project> expected = ImmutableList.of(buildTestProject());

    @SuppressWarnings("unchecked")
    HttpResponse<String> mockedResponse = Mockito.mock(HttpResponse.class);
    when(mockedResponse.statusCode()).thenReturn(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    when(mockedResponse.body()).thenReturn(objectMapper.writeValueAsString(expected));
    doReturn(mockedResponse).when(mockedHttpClient).send(any(), any());

    ProjectDao projectDao = new ProjectDao(mockedHttpClient, Dotenv.load());
    Exception exception = assertThrows(ServiceUnavailableException.class, projectDao::getAll);

    String expectedMessage = String.format("Status: %d Body: %s",
        HttpStatus.SC_INTERNAL_SERVER_ERROR, objectMapper.writeValueAsString(expected));
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void whenGetAll_withInvalidUri_throwsRuntimeException()
      throws IOException, InterruptedException {
    Collection<Project> expected = ImmutableList.of(buildTestProject());

    @SuppressWarnings("unchecked")
    HttpResponse<String> mockedResponse = Mockito.mock(HttpResponse.class);
    when(mockedResponse.statusCode()).thenReturn(HttpStatus.SC_OK);
    when(mockedResponse.body()).thenReturn(objectMapper.writeValueAsString(expected));
    doReturn(mockedResponse).when(mockedHttpClient).send(any(), any());

    Dotenv mockedDotenv = Mockito.mock(Dotenv.class);
    final String invalidUriString = "http://finance.yahoo.com/q/h?s=^IXIC";
    when(mockedDotenv.get("PROJECT_URI")).thenReturn(invalidUriString);

    ProjectDao projectDao = new ProjectDao(mockedHttpClient, mockedDotenv);
    Exception exception = assertThrows(RuntimeException.class, projectDao::getAll);

    String expectedMessage = "Illegal character in query at index 31: " + invalidUriString;
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  public void whenGetAll_clientThrowsIoException_throwsRuntimeException()
      throws IOException, InterruptedException {
    when(mockedHttpClient.send(any(), any())).thenThrow(new IOException("Bad Beef"));

    ProjectDao projectDao = new ProjectDao(mockedHttpClient, Dotenv.load());
    Exception exception = assertThrows(RuntimeException.class, projectDao::getAll);

    String expectedMessage = "Bad Beef";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  public void whenGetAll_clientThrowsInterruptedException_throwsRuntimeException()
      throws IOException, InterruptedException {
    when(mockedHttpClient.send(any(), any())).thenThrow(new InterruptedException("Bad Beef"));

    ProjectDao projectDao = new ProjectDao(mockedHttpClient, Dotenv.load());
    Exception exception = assertThrows(RuntimeException.class, projectDao::getAll);

    String expectedMessage = "Bad Beef";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  public void whenGet_happyPath_succeeds() throws IOException, InterruptedException {
    Project expected = buildTestProject();

    @SuppressWarnings("unchecked")
    HttpResponse<String> mockedResponse = Mockito.mock(HttpResponse.class);
    when(mockedResponse.statusCode()).thenReturn(HttpStatus.SC_OK);
    when(mockedResponse.body()).thenReturn(objectMapper.writeValueAsString(expected));
    doReturn(mockedResponse).when(mockedHttpClient).send(any(), any());

    ProjectDao projectDao = new ProjectDao(mockedHttpClient, Dotenv.load());
    Project project = projectDao.get(expected.getId());

    assertEquals(expected, project);
  }

  @Test
  public void whenGet_happyPath_requestIsCreatedCorrectly()
      throws IOException, InterruptedException, URISyntaxException {
    Project expected = buildTestProject();

    @SuppressWarnings("unchecked")
    HttpResponse<String> mockedResponse = Mockito.mock(HttpResponse.class);
    when(mockedResponse.statusCode()).thenReturn(HttpStatus.SC_OK);
    when(mockedResponse.body()).thenReturn(objectMapper.writeValueAsString(expected));
    doReturn(mockedResponse).when(mockedHttpClient).send(any(), any());

    Dotenv dotenv = Dotenv.load();
    String token = dotenv.get("TODOIST_API_TOKEN");
    String baseUri = dotenv.get("PROJECT_URI");
    String expectedUri = baseUri + expected.getId();
    HttpRequest expectedRequest = HttpRequest.newBuilder().uri(new URI(expectedUri))
        .header("Authorization", "Bearer " + token)
        .GET().build();

    ProjectDao projectDao = new ProjectDao(mockedHttpClient, dotenv);
    projectDao.get(expected.getId());

    ArgumentCaptor<HttpRequest> argumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    verify(mockedHttpClient).send(argumentCaptor.capture(), any());
    HttpRequest capturedArgument = argumentCaptor.getValue();
    assertEquals(expectedRequest, capturedArgument);
  }

  @Test
  public void whenGet_responseStatusInvalid_throwsServiceUnavailableException()
      throws IOException, InterruptedException {
    Project expected = buildTestProject();

    @SuppressWarnings("unchecked")
    HttpResponse<String> mockedResponse = Mockito.mock(HttpResponse.class);
    when(mockedResponse.statusCode()).thenReturn(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    when(mockedResponse.body()).thenReturn(objectMapper.writeValueAsString(expected));
    doReturn(mockedResponse).when(mockedHttpClient).send(any(), any());

    ProjectDao projectDao = new ProjectDao(mockedHttpClient, Dotenv.load());
    Exception exception = assertThrows(ServiceUnavailableException.class,
        () -> projectDao.get(expected.getId()));

    String expectedMessage = String.format("Status: %d Body:", HttpStatus.SC_INTERNAL_SERVER_ERROR);
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  public void whenGet_withInvalidUri_throwsRuntimeException()
      throws IOException, InterruptedException {
    Project expected = buildTestProject();

    @SuppressWarnings("unchecked")
    HttpResponse<String> mockedResponse = Mockito.mock(HttpResponse.class);
    when(mockedResponse.statusCode()).thenReturn(HttpStatus.SC_OK);
    when(mockedResponse.body()).thenReturn(objectMapper.writeValueAsString(expected));
    doReturn(mockedResponse).when(mockedHttpClient).send(any(), any());

    Dotenv mockedDotenv = Mockito.mock(Dotenv.class);
    final String invalidUriString = "http://finance.yahoo.com/q/h?s=^IXIC";
    when(mockedDotenv.get("PROJECT_URI")).thenReturn(invalidUriString);

    ProjectDao projectDao = new ProjectDao(mockedHttpClient, mockedDotenv);
    Exception exception = assertThrows(RuntimeException.class,
        () -> projectDao.get(expected.getId()));

    String expectedMessage = "Illegal character in query at index 31: " + invalidUriString;
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  public void whenGet_clientThrowsIoException_throwsRuntimeException()
      throws IOException, InterruptedException {
    Project expected = buildTestProject();
    when(mockedHttpClient.send(any(), any())).thenThrow(new IOException("Bad Beef"));

    ProjectDao projectDao = new ProjectDao(mockedHttpClient, Dotenv.load());
    Exception exception = assertThrows(RuntimeException.class,
        () -> projectDao.get(expected.getId()));

    String expectedMessage = "Bad Beef";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  public void whenGet_clientThrowsInterruptedException_throwsRuntimeException()
      throws IOException, InterruptedException {
    Project expected = buildTestProject();
    when(mockedHttpClient.send(any(), any())).thenThrow(new InterruptedException("Bad Beef"));

    ProjectDao projectDao = new ProjectDao(mockedHttpClient, Dotenv.load());
    Exception exception = assertThrows(RuntimeException.class,
        () -> projectDao.get(expected.getId()));

    String expectedMessage = "Bad Beef";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  public void whenCreate_happyPath_succeeds()
      throws IOException, InterruptedException, URISyntaxException {
    Project expected = buildTestProject();
    CreateArgs args = CreateArgs.builder().name(expected.getName()).build();

    @SuppressWarnings("unchecked")
    HttpResponse<String> mockedResponse = Mockito.mock(HttpResponse.class);
    when(mockedResponse.statusCode()).thenReturn(HttpStatus.SC_OK);
    when(mockedResponse.body()).thenReturn(objectMapper.writeValueAsString(expected));
    doReturn(mockedResponse).when(mockedHttpClient).send(any(), any());

    ProjectDao projectDao = new ProjectDao(mockedHttpClient, Dotenv.load());
    Project project = projectDao.create(args);

    assertEquals(expected, project);
  }

  @Test
  public void whenCreate_happyPath_requestIsCreatedCorrectly()
      throws IOException, InterruptedException, URISyntaxException {
    Project expected = buildTestProject();
    CreateArgs args = CreateArgs.builder().name(expected.getName()).build();

    @SuppressWarnings("unchecked")
    HttpResponse<String> mockedResponse = Mockito.mock(HttpResponse.class);
    when(mockedResponse.statusCode()).thenReturn(HttpStatus.SC_OK);
    when(mockedResponse.body()).thenReturn(objectMapper.writeValueAsString(expected));
    doReturn(mockedResponse).when(mockedHttpClient).send(any(), any());

    Dotenv dotenv = Dotenv.load();
    String token = dotenv.get("TODOIST_API_TOKEN");
    String baseUri = dotenv.get("PROJECT_URI");
    assert baseUri != null;
    HttpRequest expectedRequest = HttpRequest.newBuilder().uri(new URI(baseUri))
        .header("Authorization", "Bearer " + token)
        .header("Content-Type", "application/json")
        .POST(BodyPublishers.ofString(objectMapper.writeValueAsString(args))).build();

    ProjectDao projectDao = new ProjectDao(mockedHttpClient, dotenv);
    projectDao.create(args);

    ArgumentCaptor<HttpRequest> argumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    verify(mockedHttpClient).send(argumentCaptor.capture(), any());
    HttpRequest capturedArgument = argumentCaptor.getValue();
    assertEquals(expectedRequest, capturedArgument);
  }

  @Test
  public void whenCreate_responseStatusInvalid_throwsServiceUnavailableException()
      throws IOException, InterruptedException {
    Project expected = buildTestProject();
    CreateArgs args = CreateArgs.builder().name(expected.getName()).build();

    @SuppressWarnings("unchecked")
    HttpResponse<String> mockedResponse = Mockito.mock(HttpResponse.class);
    when(mockedResponse.statusCode()).thenReturn(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    when(mockedResponse.body()).thenReturn(objectMapper.writeValueAsString(expected));
    doReturn(mockedResponse).when(mockedHttpClient).send(any(), any());

    ProjectDao projectDao = new ProjectDao(mockedHttpClient, Dotenv.load());
    Exception exception = assertThrows(ServiceUnavailableException.class,
        () -> projectDao.create(args));

    String expectedMessage = String.format("Status: %d Body:", HttpStatus.SC_INTERNAL_SERVER_ERROR);
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  public void whenCreate_withInvalidUri_throwsRuntimeException()
      throws IOException, InterruptedException {
    Project expected = buildTestProject();
    CreateArgs args = CreateArgs.builder().name(expected.getName()).build();

    @SuppressWarnings("unchecked")
    HttpResponse<String> mockedResponse = Mockito.mock(HttpResponse.class);
    when(mockedResponse.statusCode()).thenReturn(HttpStatus.SC_OK);
    when(mockedResponse.body()).thenReturn(objectMapper.writeValueAsString(expected));
    doReturn(mockedResponse).when(mockedHttpClient).send(any(), any());

    Dotenv mockedDotenv = Mockito.mock(Dotenv.class);
    final String invalidUriString = "http://finance.yahoo.com/q/h?s=^IXIC";
    when(mockedDotenv.get("PROJECT_URI")).thenReturn(invalidUriString);

    ProjectDao projectDao = new ProjectDao(mockedHttpClient, mockedDotenv);
    Exception exception = assertThrows(RuntimeException.class,
        () -> projectDao.create(args));

    String expectedMessage = "Illegal character in query at index 31: " + invalidUriString;
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  public void whenCreate_clientThrowsIoException_throwsRuntimeException()
      throws IOException, InterruptedException {
    Project expected = buildTestProject();
    CreateArgs args = CreateArgs.builder().name(expected.getName()).build();

    when(mockedHttpClient.send(any(), any())).thenThrow(new IOException("Bad Beef"));

    ProjectDao projectDao = new ProjectDao(mockedHttpClient, Dotenv.load());
    Exception exception = assertThrows(RuntimeException.class,
        () -> projectDao.create(args));

    String expectedMessage = "Bad Beef";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  public void whenCreate_clientThrowsInterruptedException_throwsRuntimeException()
      throws IOException, InterruptedException {
    Project expected = buildTestProject();
    CreateArgs args = CreateArgs.builder().name(expected.getName()).build();

    when(mockedHttpClient.send(any(), any())).thenThrow(new InterruptedException("Bad Beef"));

    ProjectDao projectDao = new ProjectDao(mockedHttpClient, Dotenv.load());
    Exception exception = assertThrows(RuntimeException.class,
        () -> projectDao.create(args));

    String expectedMessage = "Bad Beef";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  public void whenUpdate_happyPath_succeeds()
      throws IOException, InterruptedException, URISyntaxException {
    Project expected = buildTestProject();
    UpdateArgs args = UpdateArgs.builder().name(expected.getName()).build();

    @SuppressWarnings("unchecked")
    HttpResponse<String> mockedResponse = Mockito.mock(HttpResponse.class);
    when(mockedResponse.statusCode()).thenReturn(HttpStatus.SC_NO_CONTENT);
    when(mockedResponse.body()).thenReturn(objectMapper.writeValueAsString(expected));
    doReturn(mockedResponse).when(mockedHttpClient).send(any(), any());

    Dotenv dotenv = Dotenv.load();
    String token = dotenv.get("TODOIST_API_TOKEN");
    String baseUri = dotenv.get("PROJECT_URI");
    String expectedUri = baseUri + expected.getId();
    HttpRequest expectedRequest = HttpRequest.newBuilder().uri(new URI(expectedUri))
        .header("Authorization", "Bearer " + token)
        .header("Content-Type", "application/json")
        .POST(BodyPublishers.ofString(objectMapper.writeValueAsString(args))).build();

    ProjectDao projectDao = new ProjectDao(mockedHttpClient, dotenv);
    projectDao.update(expected.getId(), args);

    ArgumentCaptor<HttpRequest> argumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    verify(mockedHttpClient).send(argumentCaptor.capture(), any());
    HttpRequest capturedArgument = argumentCaptor.getValue();
    assertEquals(expectedRequest, capturedArgument);
  }

  @Test
  public void whenUpdate_responseStatusInvalid_throwsServiceUnavailableException()
      throws IOException, InterruptedException {
    Project expected = buildTestProject();
    UpdateArgs args = UpdateArgs.builder().name(expected.getName()).build();

    @SuppressWarnings("unchecked")
    HttpResponse<String> mockedResponse = Mockito.mock(HttpResponse.class);
    when(mockedResponse.statusCode()).thenReturn(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    when(mockedResponse.body()).thenReturn(objectMapper.writeValueAsString(expected));
    doReturn(mockedResponse).when(mockedHttpClient).send(any(), any());

    ProjectDao projectDao = new ProjectDao(mockedHttpClient, Dotenv.load());
    Exception exception = assertThrows(ServiceUnavailableException.class,
        () -> projectDao.update(expected.getId(), args));

    String expectedMessage = String.format("Status: %d Body:", HttpStatus.SC_INTERNAL_SERVER_ERROR);
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  public void whenUpdate_withInvalidUri_throwsRuntimeException()
      throws IOException, InterruptedException {
    Project expected = buildTestProject();
    UpdateArgs args = UpdateArgs.builder().name(expected.getName()).build();

    @SuppressWarnings("unchecked")
    HttpResponse<String> mockedResponse = Mockito.mock(HttpResponse.class);
    when(mockedResponse.statusCode()).thenReturn(HttpStatus.SC_OK);
    when(mockedResponse.body()).thenReturn(objectMapper.writeValueAsString(expected));
    doReturn(mockedResponse).when(mockedHttpClient).send(any(), any());

    Dotenv mockedDotenv = Mockito.mock(Dotenv.class);
    final String invalidUriString = "http://finance.yahoo.com/q/h?s=^IXIC";
    when(mockedDotenv.get("PROJECT_URI")).thenReturn(invalidUriString);

    ProjectDao projectDao = new ProjectDao(mockedHttpClient, mockedDotenv);
    Exception exception = assertThrows(RuntimeException.class,
        () -> projectDao.update(expected.getId(), args));

    String expectedMessage = "Illegal character in query at index 31: " + invalidUriString;
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  public void whenUpdate_clientThrowsIoException_throwsRuntimeException()
      throws IOException, InterruptedException {
    Project expected = buildTestProject();
    UpdateArgs args = UpdateArgs.builder().name(expected.getName()).build();

    when(mockedHttpClient.send(any(), any())).thenThrow(new IOException("Bad Beef"));

    ProjectDao projectDao = new ProjectDao(mockedHttpClient, Dotenv.load());
    Exception exception = assertThrows(RuntimeException.class,
        () -> projectDao.update(expected.getId(), args));

    String expectedMessage = "Bad Beef";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  public void whenUpdate_clientThrowsInterruptedException_throwsRuntimeException()
      throws IOException, InterruptedException {
    Project expected = buildTestProject();
    UpdateArgs args = UpdateArgs.builder().name(expected.getName()).build();

    when(mockedHttpClient.send(any(), any())).thenThrow(new InterruptedException("Bad Beef"));

    ProjectDao projectDao = new ProjectDao(mockedHttpClient, Dotenv.load());
    Exception exception = assertThrows(RuntimeException.class,
        () -> projectDao.update(expected.getId(), args));

    String expectedMessage = "Bad Beef";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  public void whenDelete_happyPath_succeeds()
      throws IOException, InterruptedException, URISyntaxException {
    Project expected = buildTestProject();

    @SuppressWarnings("unchecked")
    HttpResponse<String> mockedResponse = Mockito.mock(HttpResponse.class);
    when(mockedResponse.statusCode()).thenReturn(HttpStatus.SC_NO_CONTENT);
    when(mockedResponse.body()).thenReturn(objectMapper.writeValueAsString(expected));
    doReturn(mockedResponse).when(mockedHttpClient).send(any(), any());

    Dotenv dotenv = Dotenv.load();
    String token = dotenv.get("TODOIST_API_TOKEN");
    String baseUri = dotenv.get("PROJECT_URI");
    String expectedUri = baseUri + expected.getId();
    HttpRequest expectedRequest = HttpRequest.newBuilder().uri(new URI(expectedUri))
        .header("Authorization", "Bearer " + token)
        .DELETE().build();

    ProjectDao projectDao = new ProjectDao(mockedHttpClient, dotenv);
    projectDao.delete(expected.getId());

    ArgumentCaptor<HttpRequest> argumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    verify(mockedHttpClient).send(argumentCaptor.capture(), any());
    HttpRequest capturedArgument = argumentCaptor.getValue();
    assertEquals(expectedRequest, capturedArgument);
  }

  @Test
  public void whenDelete_responseStatusInvalid_throwsServiceUnavailableException()
      throws IOException, InterruptedException {
    Project expected = buildTestProject();

    @SuppressWarnings("unchecked")
    HttpResponse<String> mockedResponse = Mockito.mock(HttpResponse.class);
    when(mockedResponse.statusCode()).thenReturn(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    when(mockedResponse.body()).thenReturn(objectMapper.writeValueAsString(expected));
    doReturn(mockedResponse).when(mockedHttpClient).send(any(), any());

    ProjectDao projectDao = new ProjectDao(mockedHttpClient, Dotenv.load());
    Exception exception = assertThrows(ServiceUnavailableException.class,
        () -> projectDao.delete(expected.getId()));

    String expectedMessage = String.format("Status: %d Body:", HttpStatus.SC_INTERNAL_SERVER_ERROR);
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  public void whenDelete_withInvalidUri_throwsRuntimeException()
      throws IOException, InterruptedException {
    Project expected = buildTestProject();

    @SuppressWarnings("unchecked")
    HttpResponse<String> mockedResponse = Mockito.mock(HttpResponse.class);
    when(mockedResponse.statusCode()).thenReturn(HttpStatus.SC_OK);
    when(mockedResponse.body()).thenReturn(objectMapper.writeValueAsString(expected));
    doReturn(mockedResponse).when(mockedHttpClient).send(any(), any());

    Dotenv mockedDotenv = Mockito.mock(Dotenv.class);
    final String invalidUriString = "http://finance.yahoo.com/q/h?s=^IXIC";
    when(mockedDotenv.get("PROJECT_URI")).thenReturn(invalidUriString);

    ProjectDao projectDao = new ProjectDao(mockedHttpClient, mockedDotenv);
    Exception exception = assertThrows(RuntimeException.class,
        () -> projectDao.delete(expected.getId()));

    String expectedMessage = "URISyntaxException";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  public void whenDelete_clientThrowsIoException_throwsRuntimeException()
      throws IOException, InterruptedException {
    Project expected = buildTestProject();

    when(mockedHttpClient.send(any(), any())).thenThrow(new IOException("Bad Beef"));

    ProjectDao projectDao = new ProjectDao(mockedHttpClient, Dotenv.load());
    Exception exception = assertThrows(RuntimeException.class,
        () -> projectDao.delete(expected.getId()));

    String expectedMessage = "Bad Beef";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  public void whenDelete_clientThrowsInterruptedException_throwsRuntimeException()
      throws IOException, InterruptedException {
    Project expected = buildTestProject();

    when(mockedHttpClient.send(any(), any())).thenThrow(new InterruptedException("Bad Beef"));

    ProjectDao projectDao = new ProjectDao(mockedHttpClient, Dotenv.load());
    Exception exception = assertThrows(RuntimeException.class,
        () -> projectDao.delete(expected.getId()));

    String expectedMessage = "Bad Beef";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  static Project buildTestProject() {
    return Project.builder().id(1).name("name").color(Colors.BLUE).parentId(1).order(1)
        .commentCount(1)
        .shared(true).favorite(true).inboxProject(true).teamInbox(true).syncId(1).url("url")
        .build();
  }
}
