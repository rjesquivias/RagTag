package com.rjesquivias.todoist.dao;

import static com.rjesquivias.todoist.dao.IBaseDao.noContentPredicate;
import static com.rjesquivias.todoist.dao.IBaseDao.okPredicate;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rjesquivias.todoist.domain.Label;
import com.rjesquivias.todoist.util.http.HttpRequestHelper;
import com.rjesquivias.todoist.util.http.ResponsePredicate;
import io.github.cdimascio.dotenv.Dotenv;
import java.net.http.HttpRequest;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class LabelDaoTest implements IBaseDaoTest {

  @Test
  public void whenGetAll_callsHttpRequestHelperWithCorrectArguments() {
    HttpRequestHelper mockedHttpRequestHelper = Mockito.mock(HttpRequestHelper.class);
    Dotenv mockedDotenv = Mockito.mock(Dotenv.class);
    when(mockedDotenv.get("LABEL_URI")).thenReturn(validUriString);
    when(mockedDotenv.get("TODOIST_API_TOKEN")).thenReturn(testToken);
    LabelDao labelDao = new LabelDao(mockedHttpRequestHelper, mockedDotenv);

    labelDao.getAll();

    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    ArgumentCaptor<ResponsePredicate> responsePredicateArgumentCaptor = ArgumentCaptor.forClass(
        ResponsePredicate.class);
    ArgumentCaptor<Class> classArgumentCaptor = ArgumentCaptor.forClass(Class.class);

    verify(mockedHttpRequestHelper).makeCollectionRequest(requestArgumentCaptor.capture(),
        responsePredicateArgumentCaptor.capture(), classArgumentCaptor.capture());

    HttpRequest expectedHttpRequest = httpRequestFactory.buildGet(validUriString);

    assertEquals(expectedHttpRequest, requestArgumentCaptor.getValue());
    assertEquals(okPredicate, responsePredicateArgumentCaptor.getValue());
    assertEquals(Label.class, classArgumentCaptor.getValue());
  }

  @Test
  public void whenCreate_callsHttpRequestHelperWithCorrectArguments() {
    HttpRequestHelper mockedHttpRequestHelper = Mockito.mock(HttpRequestHelper.class);
    Dotenv mockedDotenv = Mockito.mock(Dotenv.class);
    when(mockedDotenv.get("LABEL_URI")).thenReturn(validUriString);
    when(mockedDotenv.get("TODOIST_API_TOKEN")).thenReturn(testToken);
    LabelDao labelDao = new LabelDao(mockedHttpRequestHelper, mockedDotenv);

    labelDao.create(createLabelArgs);

    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    ArgumentCaptor<ResponsePredicate> responsePredicateArgumentCaptor = ArgumentCaptor.forClass(
        ResponsePredicate.class);
    ArgumentCaptor<Class> classArgumentCaptor = ArgumentCaptor.forClass(Class.class);

    verify(mockedHttpRequestHelper).makeRequest(requestArgumentCaptor.capture(),
        responsePredicateArgumentCaptor.capture(), classArgumentCaptor.capture());

    HttpRequest expectedHttpRequest = httpRequestFactory.buildPost(validUriString, createLabelArgs);

    assertEquals(expectedHttpRequest, requestArgumentCaptor.getValue());
    assertEquals(okPredicate, responsePredicateArgumentCaptor.getValue());
    assertEquals(Label.class, classArgumentCaptor.getValue());
  }

  @Test
  public void whenGet_callsHttpRequestHelperWithCorrectArguments() {
    HttpRequestHelper mockedHttpRequestHelper = Mockito.mock(HttpRequestHelper.class);
    Dotenv mockedDotenv = Mockito.mock(Dotenv.class);
    when(mockedDotenv.get("LABEL_URI")).thenReturn(validUriString);
    when(mockedDotenv.get("TODOIST_API_TOKEN")).thenReturn(testToken);
    LabelDao labelDao = new LabelDao(mockedHttpRequestHelper, mockedDotenv);

    labelDao.get(testLabelId);

    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    ArgumentCaptor<ResponsePredicate> responsePredicateArgumentCaptor = ArgumentCaptor.forClass(
        ResponsePredicate.class);
    ArgumentCaptor<Class> classArgumentCaptor = ArgumentCaptor.forClass(Class.class);

    verify(mockedHttpRequestHelper).makeRequest(requestArgumentCaptor.capture(),
        responsePredicateArgumentCaptor.capture(), classArgumentCaptor.capture());

    HttpRequest expectedHttpRequest = httpRequestFactory.buildGet(validUriString + testLabelId);

    assertEquals(expectedHttpRequest, requestArgumentCaptor.getValue());
    assertEquals(okPredicate, responsePredicateArgumentCaptor.getValue());
    assertEquals(Label.class, classArgumentCaptor.getValue());
  }

  @Test
  public void whenUpdate_callsHttpRequestHelperWithCorrectArguments() {
    HttpRequestHelper mockedHttpRequestHelper = Mockito.mock(HttpRequestHelper.class);
    Dotenv mockedDotenv = Mockito.mock(Dotenv.class);
    when(mockedDotenv.get("LABEL_URI")).thenReturn(validUriString);
    when(mockedDotenv.get("TODOIST_API_TOKEN")).thenReturn(testToken);
    LabelDao labelDao = new LabelDao(mockedHttpRequestHelper, mockedDotenv);

    labelDao.update(testLabelId, updateLabelArgs);

    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    ArgumentCaptor<ResponsePredicate> responsePredicateArgumentCaptor = ArgumentCaptor.forClass(
        ResponsePredicate.class);
    ArgumentCaptor<Class> classArgumentCaptor = ArgumentCaptor.forClass(Class.class);

    verify(mockedHttpRequestHelper).makeRequest(requestArgumentCaptor.capture(),
        responsePredicateArgumentCaptor.capture(), classArgumentCaptor.capture());

    HttpRequest expectedHttpRequest = httpRequestFactory.buildPost(validUriString + testLabelId,
        updateLabelArgs);

    assertEquals(expectedHttpRequest, requestArgumentCaptor.getValue());
    assertEquals(noContentPredicate, responsePredicateArgumentCaptor.getValue());
    assertEquals(Label.class, classArgumentCaptor.getValue());
  }

  @Test
  public void whenDelete_callsHttpRequestHelperWithCorrectArguments() {
    HttpRequestHelper mockedHttpRequestHelper = Mockito.mock(HttpRequestHelper.class);
    Dotenv mockedDotenv = Mockito.mock(Dotenv.class);
    when(mockedDotenv.get("LABEL_URI")).thenReturn(validUriString);
    when(mockedDotenv.get("TODOIST_API_TOKEN")).thenReturn(testToken);
    LabelDao labelDao = new LabelDao(mockedHttpRequestHelper, mockedDotenv);

    labelDao.delete(testLabelId);

    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    ArgumentCaptor<ResponsePredicate> responsePredicateArgumentCaptor = ArgumentCaptor.forClass(
        ResponsePredicate.class);
    ArgumentCaptor<Class> classArgumentCaptor = ArgumentCaptor.forClass(Class.class);

    verify(mockedHttpRequestHelper).makeRequest(requestArgumentCaptor.capture(),
        responsePredicateArgumentCaptor.capture(), classArgumentCaptor.capture());
    
    HttpRequest expectedHttpRequest = httpRequestFactory.buildDelete(validUriString + testLabelId);

    assertEquals(expectedHttpRequest, requestArgumentCaptor.getValue());
    assertEquals(noContentPredicate, responsePredicateArgumentCaptor.getValue());
    assertEquals(Label.class, classArgumentCaptor.getValue());
  }
}
