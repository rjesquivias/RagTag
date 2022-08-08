package com.rjesquivias.todoist.dao;

import static com.rjesquivias.todoist.dao.IBaseDao.noContentPredicate;
import static com.rjesquivias.todoist.dao.IBaseDao.okPredicate;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rjesquivias.todoist.dao.ITaskDao.UpdateArgs;
import com.rjesquivias.todoist.domain.Task;
import com.rjesquivias.todoist.util.http.HttpRequestHelper;
import com.rjesquivias.todoist.util.http.ResponsePredicate;
import io.github.cdimascio.dotenv.Dotenv;
import java.net.http.HttpRequest;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class TaskDaoTest implements IBaseDaoTest {

  @Test
  public void whenGetAllActive_callsHttpRequestHelperWithCorrectArguments() {
    HttpRequestHelper mockedHttpRequestHelper = Mockito.mock(HttpRequestHelper.class);
    Dotenv mockedDotenv = Mockito.mock(Dotenv.class);
    when(mockedDotenv.get("TASK_URI")).thenReturn(validUriString);
    when(mockedDotenv.get("TODOIST_API_TOKEN")).thenReturn(testToken);
    TaskDao taskDao = new TaskDao(mockedHttpRequestHelper, mockedDotenv);

    taskDao.getAllActive(getAllActiveTaskArgs);

    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    ArgumentCaptor<ResponsePredicate> responsePredicateArgumentCaptor = ArgumentCaptor.forClass(
        ResponsePredicate.class);
    ArgumentCaptor<Class> classArgumentCaptor = ArgumentCaptor.forClass(Class.class);

    verify(mockedHttpRequestHelper).makeCollectionRequest(requestArgumentCaptor.capture(),
        responsePredicateArgumentCaptor.capture(), classArgumentCaptor.capture());

    HttpRequest expectedHttpRequest = httpRequestFactory.buildGet(validUriString);
    assertEquals(expectedHttpRequest, requestArgumentCaptor.getValue());
    assertEquals(okPredicate, responsePredicateArgumentCaptor.getValue());
    assertEquals(Task.class, classArgumentCaptor.getValue());
  }

  @Test
  public void whenCreate_callsHttpRequestHelperWithCorrectArguments() {
    HttpRequestHelper mockedHttpRequestHelper = Mockito.mock(HttpRequestHelper.class);
    Dotenv mockedDotenv = Mockito.mock(Dotenv.class);
    when(mockedDotenv.get("TASK_URI")).thenReturn(validUriString);
    when(mockedDotenv.get("TODOIST_API_TOKEN")).thenReturn(testToken);
    TaskDao taskDao = new TaskDao(mockedHttpRequestHelper, mockedDotenv);

    taskDao.create(createTaskArgs);

    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    ArgumentCaptor<ResponsePredicate> responsePredicateArgumentCaptor = ArgumentCaptor.forClass(
        ResponsePredicate.class);
    ArgumentCaptor<Class> classArgumentCaptor = ArgumentCaptor.forClass(Class.class);

    verify(mockedHttpRequestHelper).makeRequest(requestArgumentCaptor.capture(),
        responsePredicateArgumentCaptor.capture(), classArgumentCaptor.capture());

    HttpRequest expectedHttpRequest = httpRequestFactory.buildPost(validUriString, createTaskArgs);
    assertEquals(expectedHttpRequest, requestArgumentCaptor.getValue());
    assertEquals(okPredicate, responsePredicateArgumentCaptor.getValue());
    assertEquals(Task.class, classArgumentCaptor.getValue());
  }

  @Test
  public void whenGetActive_callsHttpRequestHelperWithCorrectArguments() {
    HttpRequestHelper mockedHttpRequestHelper = Mockito.mock(HttpRequestHelper.class);
    Dotenv mockedDotenv = Mockito.mock(Dotenv.class);
    when(mockedDotenv.get("TASK_URI")).thenReturn(validUriString);
    when(mockedDotenv.get("TODOIST_API_TOKEN")).thenReturn(testToken);
    TaskDao taskDao = new TaskDao(mockedHttpRequestHelper, mockedDotenv);

    taskDao.getActive(testTaskId);

    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    ArgumentCaptor<ResponsePredicate> responsePredicateArgumentCaptor = ArgumentCaptor.forClass(
        ResponsePredicate.class);
    ArgumentCaptor<Class> classArgumentCaptor = ArgumentCaptor.forClass(Class.class);

    verify(mockedHttpRequestHelper).makeRequest(requestArgumentCaptor.capture(),
        responsePredicateArgumentCaptor.capture(), classArgumentCaptor.capture());

    HttpRequest expectedHttpRequest = httpRequestFactory.buildGet(validUriString + testTaskId);

    assertEquals(expectedHttpRequest, requestArgumentCaptor.getValue());
    assertEquals(okPredicate, responsePredicateArgumentCaptor.getValue());
    assertEquals(Task.class, classArgumentCaptor.getValue());
  }

  @Test
  public void whenUpdate_callsHttpRequestHelperWithCorrectArguments() {
    HttpRequestHelper mockedHttpRequestHelper = Mockito.mock(HttpRequestHelper.class);
    Dotenv mockedDotenv = Mockito.mock(Dotenv.class);
    when(mockedDotenv.get("TASK_URI")).thenReturn(validUriString);
    when(mockedDotenv.get("TODOIST_API_TOKEN")).thenReturn(testToken);
    TaskDao taskDao = new TaskDao(mockedHttpRequestHelper, mockedDotenv);

    taskDao.update(testTaskId, UpdateArgs.builder().build());

    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    ArgumentCaptor<ResponsePredicate> responsePredicateArgumentCaptor = ArgumentCaptor.forClass(
        ResponsePredicate.class);
    ArgumentCaptor<Class> classArgumentCaptor = ArgumentCaptor.forClass(Class.class);

    verify(mockedHttpRequestHelper).makeRequest(requestArgumentCaptor.capture(),
        responsePredicateArgumentCaptor.capture(), classArgumentCaptor.capture());

    HttpRequest expectedHttpRequest = httpRequestFactory.buildPost(validUriString + testTaskId, "");

    assertEquals(expectedHttpRequest, requestArgumentCaptor.getValue());
    assertEquals(noContentPredicate, responsePredicateArgumentCaptor.getValue());
    assertEquals(Task.class, classArgumentCaptor.getValue());
  }

  @Test
  public void whenClose_callsHttpRequestHelperWithCorrectArguments() {
    HttpRequestHelper mockedHttpRequestHelper = Mockito.mock(HttpRequestHelper.class);
    Dotenv mockedDotenv = Mockito.mock(Dotenv.class);
    when(mockedDotenv.get("TASK_URI")).thenReturn(validUriString);
    when(mockedDotenv.get("TODOIST_API_TOKEN")).thenReturn(testToken);
    TaskDao taskDao = new TaskDao(mockedHttpRequestHelper, mockedDotenv);

    taskDao.close(testTaskId);

    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    ArgumentCaptor<ResponsePredicate> responsePredicateArgumentCaptor = ArgumentCaptor.forClass(
        ResponsePredicate.class);
    ArgumentCaptor<Class> classArgumentCaptor = ArgumentCaptor.forClass(Class.class);

    verify(mockedHttpRequestHelper).makeRequest(requestArgumentCaptor.capture(),
        responsePredicateArgumentCaptor.capture(), classArgumentCaptor.capture());

    HttpRequest expectedHttpRequest = httpRequestFactory.buildPost(
        validUriString + testTaskId + "/close", "");

    assertEquals(expectedHttpRequest, requestArgumentCaptor.getValue());
    assertEquals(noContentPredicate, responsePredicateArgumentCaptor.getValue());
    assertEquals(Task.class, classArgumentCaptor.getValue());
  }

  @Test
  public void whenReOpen_callsHttpRequestHelperWithCorrectArguments() {
    HttpRequestHelper mockedHttpRequestHelper = Mockito.mock(HttpRequestHelper.class);
    Dotenv mockedDotenv = Mockito.mock(Dotenv.class);
    when(mockedDotenv.get("TASK_URI")).thenReturn(validUriString);
    when(mockedDotenv.get("TODOIST_API_TOKEN")).thenReturn(testToken);
    TaskDao taskDao = new TaskDao(mockedHttpRequestHelper, mockedDotenv);

    taskDao.reOpen(testTaskId);

    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    ArgumentCaptor<ResponsePredicate> responsePredicateArgumentCaptor = ArgumentCaptor.forClass(
        ResponsePredicate.class);
    ArgumentCaptor<Class> classArgumentCaptor = ArgumentCaptor.forClass(Class.class);

    verify(mockedHttpRequestHelper).makeRequest(requestArgumentCaptor.capture(),
        responsePredicateArgumentCaptor.capture(), classArgumentCaptor.capture());

    HttpRequest expectedHttpRequest = httpRequestFactory.buildPost(
        validUriString + testTaskId + "/reopen", "");

    assertEquals(expectedHttpRequest, requestArgumentCaptor.getValue());
    assertEquals(noContentPredicate, responsePredicateArgumentCaptor.getValue());
    assertEquals(Task.class, classArgumentCaptor.getValue());
  }

  @Test
  public void whenDelete_callsHttpRequestHelperWithCorrectArguments() {
    HttpRequestHelper mockedHttpRequestHelper = Mockito.mock(HttpRequestHelper.class);
    Dotenv mockedDotenv = Mockito.mock(Dotenv.class);
    when(mockedDotenv.get("TASK_URI")).thenReturn(validUriString);
    when(mockedDotenv.get("TODOIST_API_TOKEN")).thenReturn(testToken);
    TaskDao taskDao = new TaskDao(mockedHttpRequestHelper, mockedDotenv);

    taskDao.delete(testTaskId);

    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    ArgumentCaptor<ResponsePredicate> responsePredicateArgumentCaptor = ArgumentCaptor.forClass(
        ResponsePredicate.class);
    ArgumentCaptor<Class> classArgumentCaptor = ArgumentCaptor.forClass(Class.class);

    verify(mockedHttpRequestHelper).makeRequest(requestArgumentCaptor.capture(),
        responsePredicateArgumentCaptor.capture(), classArgumentCaptor.capture());

    HttpRequest expectedHttpRequest = httpRequestFactory.buildDelete(
        validUriString + testTaskId);

    assertEquals(expectedHttpRequest, requestArgumentCaptor.getValue());
    assertEquals(noContentPredicate, responsePredicateArgumentCaptor.getValue());
    assertEquals(Task.class, classArgumentCaptor.getValue());
  }
}
