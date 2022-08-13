package com.rjesquivias.todoist;

import static com.rjesquivias.todoist.Predicates.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import java.net.http.HttpRequest;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static  com.rjesquivias.todoist.TestConstants.*;

@SuppressWarnings("rawtypes")
public class CommentDaoTest {

  @Test
  public void whenGetAllInProject_callsHttpRequestHelperWithCorrectArguments() {
    HttpRequestHelper mockedHttpRequestHelper = Mockito.mock(HttpRequestHelper.class);
    CommentDao commentDao = new CommentDao(mockedHttpRequestHelper, validUriString, testToken);

    commentDao.getAllInProject(testProjectId);

    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    ArgumentCaptor<ResponsePredicate> responsePredicateArgumentCaptor = ArgumentCaptor.forClass(
        ResponsePredicate.class);
    ArgumentCaptor<Class> classArgumentCaptor = ArgumentCaptor.forClass(Class.class);

    verify(mockedHttpRequestHelper).makeCollectionRequest(requestArgumentCaptor.capture(),
        responsePredicateArgumentCaptor.capture(), classArgumentCaptor.capture());

    HttpRequest expectedHttpRequest = httpRequestFactory.buildGet(
        String.format("%s?project_id=%d", validUriString, testProjectId));

    assertEquals(expectedHttpRequest, requestArgumentCaptor.getValue());
    assertEquals(okPredicate, responsePredicateArgumentCaptor.getValue());
    assertEquals(ImmutableComment.class, classArgumentCaptor.getValue());
  }

  @Test
  public void whenGetAllInTask_callsHttpRequestHelperWithCorrectArguments() {
    HttpRequestHelper mockedHttpRequestHelper = Mockito.mock(HttpRequestHelper.class);
    CommentDao commentDao = new CommentDao(mockedHttpRequestHelper, validUriString, testToken);

    commentDao.getAllInTask(testTaskId);

    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    ArgumentCaptor<ResponsePredicate> responsePredicateArgumentCaptor = ArgumentCaptor.forClass(
        ResponsePredicate.class);
    ArgumentCaptor<Class> classArgumentCaptor = ArgumentCaptor.forClass(Class.class);

    verify(mockedHttpRequestHelper).makeCollectionRequest(requestArgumentCaptor.capture(),
        responsePredicateArgumentCaptor.capture(), classArgumentCaptor.capture());

    HttpRequest expectedHttpRequest = httpRequestFactory.buildGet(
        String.format("%s?task_id=%d", validUriString, testTaskId));

    assertEquals(expectedHttpRequest, requestArgumentCaptor.getValue());
    assertEquals(okPredicate, responsePredicateArgumentCaptor.getValue());
    assertEquals(ImmutableComment.class, classArgumentCaptor.getValue());
  }

  @Test
  public void whenCreate_callsHttpRequestHelperWithCorrectArguments() {
    HttpRequestHelper mockedHttpRequestHelper = Mockito.mock(HttpRequestHelper.class);
    CommentDao commentDao = new CommentDao(mockedHttpRequestHelper, validUriString, testToken);

    commentDao.create(createCommentArgs);

    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    ArgumentCaptor<ResponsePredicate> responsePredicateArgumentCaptor = ArgumentCaptor.forClass(
        ResponsePredicate.class);
    ArgumentCaptor<Class> classArgumentCaptor = ArgumentCaptor.forClass(Class.class);

    verify(mockedHttpRequestHelper).makeRequest(requestArgumentCaptor.capture(),
        responsePredicateArgumentCaptor.capture(), classArgumentCaptor.capture());

    HttpRequest expectedHttpRequest = httpRequestFactory.buildPost(validUriString,
        createCommentArgs);

    assertEquals(expectedHttpRequest, requestArgumentCaptor.getValue());
    assertEquals(okPredicate, responsePredicateArgumentCaptor.getValue());
    assertEquals(ImmutableComment.class, classArgumentCaptor.getValue());
  }

  @Test
  public void whenGet_callsHttpRequestHelperWithCorrectArguments() {
    HttpRequestHelper mockedHttpRequestHelper = Mockito.mock(HttpRequestHelper.class);
    CommentDao commentDao = new CommentDao(mockedHttpRequestHelper, validUriString, testToken);

    commentDao.get(testCommentId);

    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    ArgumentCaptor<ResponsePredicate> responsePredicateArgumentCaptor = ArgumentCaptor.forClass(
        ResponsePredicate.class);
    ArgumentCaptor<Class> classArgumentCaptor = ArgumentCaptor.forClass(Class.class);

    verify(mockedHttpRequestHelper).makeRequest(requestArgumentCaptor.capture(),
        responsePredicateArgumentCaptor.capture(), classArgumentCaptor.capture());

    HttpRequest expectedHttpRequest = httpRequestFactory.buildGet(validUriString + testCommentId);

    assertEquals(expectedHttpRequest, requestArgumentCaptor.getValue());
    assertEquals(okPredicate, responsePredicateArgumentCaptor.getValue());
    assertEquals(ImmutableComment.class, classArgumentCaptor.getValue());
  }

  @Test
  public void whenUpdate_callsHttpRequestHelperWithCorrectArguments() {
    HttpRequestHelper mockedHttpRequestHelper = Mockito.mock(HttpRequestHelper.class);
    CommentDao commentDao = new CommentDao(mockedHttpRequestHelper, validUriString, testToken);

    commentDao.update(testCommentId, testContent);

    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    ArgumentCaptor<ResponsePredicate> responsePredicateArgumentCaptor = ArgumentCaptor.forClass(
        ResponsePredicate.class);
    ArgumentCaptor<Class> classArgumentCaptor = ArgumentCaptor.forClass(Class.class);

    verify(mockedHttpRequestHelper).makeRequest(requestArgumentCaptor.capture(),
        responsePredicateArgumentCaptor.capture(), classArgumentCaptor.capture());

    HttpRequest expectedHttpRequest = httpRequestFactory.buildPost(validUriString + testCommentId,
        String.format("{\"content\": \"%s\"}", testContent));

    assertEquals(expectedHttpRequest, requestArgumentCaptor.getValue());
    assertEquals(noContentPredicate, responsePredicateArgumentCaptor.getValue());
    assertEquals(ImmutableComment.class, classArgumentCaptor.getValue());
  }

  @Test
  public void whenDelete_callsHttpRequestHelperWithCorrectArguments() {
    HttpRequestHelper mockedHttpRequestHelper = Mockito.mock(HttpRequestHelper.class);
    CommentDao commentDao = new CommentDao(mockedHttpRequestHelper, validUriString, testToken);

    commentDao.delete(testCommentId);

    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    ArgumentCaptor<ResponsePredicate> responsePredicateArgumentCaptor = ArgumentCaptor.forClass(
        ResponsePredicate.class);
    ArgumentCaptor<Class> classArgumentCaptor = ArgumentCaptor.forClass(Class.class);

    verify(mockedHttpRequestHelper).makeRequest(requestArgumentCaptor.capture(),
        responsePredicateArgumentCaptor.capture(), classArgumentCaptor.capture());

    HttpRequest expectedHttpRequest = httpRequestFactory.buildDelete(
        validUriString + testCommentId);

    assertEquals(expectedHttpRequest, requestArgumentCaptor.getValue());
    assertEquals(noContentPredicate, responsePredicateArgumentCaptor.getValue());
    assertEquals(ImmutableComment.class, classArgumentCaptor.getValue());
  }
}
