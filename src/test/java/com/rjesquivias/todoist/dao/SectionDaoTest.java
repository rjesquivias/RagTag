package com.rjesquivias.todoist.dao;

import static com.rjesquivias.todoist.dao.SectionDao.noContentPredicate;
import static com.rjesquivias.todoist.dao.SectionDao.okPredicate;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rjesquivias.todoist.domain.Section;
import com.rjesquivias.todoist.util.http.HttpRequestHelper;
import com.rjesquivias.todoist.util.http.ResponsePredicate;
import io.github.cdimascio.dotenv.Dotenv;
import java.net.http.HttpRequest;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class SectionDaoTest implements IBaseDaoTest {

  @Test
  public void whenGetAll_callsHttpRequestHelperWithCorrectArguments() {
    HttpRequestHelper mockedHttpRequestHelper = Mockito.mock(HttpRequestHelper.class);
    Dotenv mockedDotenv = Mockito.mock(Dotenv.class);
    when(mockedDotenv.get("SECTION_URI")).thenReturn(validUriString);
    when(mockedDotenv.get("TODOIST_API_TOKEN")).thenReturn(testToken);
    SectionDao sectionDao = new SectionDao(mockedHttpRequestHelper, mockedDotenv);

    sectionDao.getAll();

    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    ArgumentCaptor<ResponsePredicate> responsePredicateArgumentCaptor = ArgumentCaptor.forClass(
        ResponsePredicate.class);
    ArgumentCaptor<Class> classArgumentCaptor = ArgumentCaptor.forClass(Class.class);

    verify(mockedHttpRequestHelper).makeCollectionRequest(requestArgumentCaptor.capture(),
        responsePredicateArgumentCaptor.capture(), classArgumentCaptor.capture());

    HttpRequest expectedHttpRequest = httpRequestFactory.buildGet(validUriString);
    assertEquals(expectedHttpRequest, requestArgumentCaptor.getValue());
    assertEquals(okPredicate, responsePredicateArgumentCaptor.getValue());
    assertEquals(Section.class, classArgumentCaptor.getValue());
  }

  @Test
  public void whenGetAll_withProjectId_callsHttpRequestHelperWithCorrectArguments() {
    HttpRequestHelper mockedHttpRequestHelper = Mockito.mock(HttpRequestHelper.class);
    Dotenv mockedDotenv = Mockito.mock(Dotenv.class);
    when(mockedDotenv.get("SECTION_URI")).thenReturn(validUriString);
    when(mockedDotenv.get("TODOIST_API_TOKEN")).thenReturn(testToken);
    SectionDao sectionDao = new SectionDao(mockedHttpRequestHelper, mockedDotenv);

    sectionDao.getAll(testProjectId);

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
    assertEquals(Section.class, classArgumentCaptor.getValue());
  }

  @Test
  public void whenCreate_callsHttpRequestHelperWithCorrectArguments() {
    HttpRequestHelper mockedHttpRequestHelper = Mockito.mock(HttpRequestHelper.class);
    Dotenv mockedDotenv = Mockito.mock(Dotenv.class);
    when(mockedDotenv.get("SECTION_URI")).thenReturn(validUriString);
    when(mockedDotenv.get("TODOIST_API_TOKEN")).thenReturn(testToken);
    SectionDao sectionDao = new SectionDao(mockedHttpRequestHelper, mockedDotenv);

    sectionDao.create(createSectionArgs);

    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    ArgumentCaptor<ResponsePredicate> responsePredicateArgumentCaptor = ArgumentCaptor.forClass(
        ResponsePredicate.class);
    ArgumentCaptor<Class> classArgumentCaptor = ArgumentCaptor.forClass(Class.class);

    verify(mockedHttpRequestHelper).makeRequest(requestArgumentCaptor.capture(),
        responsePredicateArgumentCaptor.capture(), classArgumentCaptor.capture());

    HttpRequest expectedHttpRequest = httpRequestFactory.buildPost(validUriString,
        createSectionArgs);

    assertEquals(expectedHttpRequest, requestArgumentCaptor.getValue());
    assertEquals(okPredicate, responsePredicateArgumentCaptor.getValue());
    assertEquals(Section.class, classArgumentCaptor.getValue());
  }

  @Test
  public void whenGet_callsHttpRequestHelperWithCorrectArguments() {
    HttpRequestHelper mockedHttpRequestHelper = Mockito.mock(HttpRequestHelper.class);
    Dotenv mockedDotenv = Mockito.mock(Dotenv.class);
    when(mockedDotenv.get("SECTION_URI")).thenReturn(validUriString);
    when(mockedDotenv.get("TODOIST_API_TOKEN")).thenReturn(testToken);
    SectionDao sectionDao = new SectionDao(mockedHttpRequestHelper, mockedDotenv);

    sectionDao.get(testSectionId);

    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    ArgumentCaptor<ResponsePredicate> responsePredicateArgumentCaptor = ArgumentCaptor.forClass(
        ResponsePredicate.class);
    ArgumentCaptor<Class> classArgumentCaptor = ArgumentCaptor.forClass(Class.class);

    verify(mockedHttpRequestHelper).makeRequest(requestArgumentCaptor.capture(),
        responsePredicateArgumentCaptor.capture(), classArgumentCaptor.capture());

    HttpRequest expectedHttpRequest = httpRequestFactory.buildGet(validUriString + testSectionId);

    assertEquals(expectedHttpRequest, requestArgumentCaptor.getValue());
    assertEquals(okPredicate, responsePredicateArgumentCaptor.getValue());
    assertEquals(Section.class, classArgumentCaptor.getValue());
  }

  @Test
  public void whenUpdate_callsHttpRequestHelperWithCorrectArguments() {
    HttpRequestHelper mockedHttpRequestHelper = Mockito.mock(HttpRequestHelper.class);
    Dotenv mockedDotenv = Mockito.mock(Dotenv.class);
    when(mockedDotenv.get("SECTION_URI")).thenReturn(validUriString);
    when(mockedDotenv.get("TODOIST_API_TOKEN")).thenReturn(testToken);
    SectionDao sectionDao = new SectionDao(mockedHttpRequestHelper, mockedDotenv);

    sectionDao.update(testSectionId, testUpdatedSectionName);

    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    ArgumentCaptor<ResponsePredicate> responsePredicateArgumentCaptor = ArgumentCaptor.forClass(
        ResponsePredicate.class);
    ArgumentCaptor<Class> classArgumentCaptor = ArgumentCaptor.forClass(Class.class);

    verify(mockedHttpRequestHelper).makeRequest(requestArgumentCaptor.capture(),
        responsePredicateArgumentCaptor.capture(), classArgumentCaptor.capture());

    HttpRequest expectedHttpRequest = httpRequestFactory.buildPost(validUriString + testSectionId,
        String.format("{\"name\": \"%s\"}", testUpdatedSectionName));

    assertEquals(expectedHttpRequest, requestArgumentCaptor.getValue());
    assertEquals(noContentPredicate, responsePredicateArgumentCaptor.getValue());
    assertEquals(Section.class, classArgumentCaptor.getValue());
  }

  @Test
  public void whenDelete_callsHttpRequestHelperWithCorrectArguments() {
    HttpRequestHelper mockedHttpRequestHelper = Mockito.mock(HttpRequestHelper.class);
    Dotenv mockedDotenv = Mockito.mock(Dotenv.class);
    when(mockedDotenv.get("SECTION_URI")).thenReturn(validUriString);
    when(mockedDotenv.get("TODOIST_API_TOKEN")).thenReturn(testToken);
    SectionDao sectionDao = new SectionDao(mockedHttpRequestHelper, mockedDotenv);

    sectionDao.delete(testSectionId);

    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    ArgumentCaptor<ResponsePredicate> responsePredicateArgumentCaptor = ArgumentCaptor.forClass(
        ResponsePredicate.class);
    ArgumentCaptor<Class> classArgumentCaptor = ArgumentCaptor.forClass(Class.class);

    verify(mockedHttpRequestHelper).makeRequest(requestArgumentCaptor.capture(),
        responsePredicateArgumentCaptor.capture(), classArgumentCaptor.capture());

    HttpRequest expectedHttpRequest = httpRequestFactory.buildDelete(
        validUriString + testSectionId);

    assertEquals(expectedHttpRequest, requestArgumentCaptor.getValue());
    assertEquals(noContentPredicate, responsePredicateArgumentCaptor.getValue());
    assertEquals(Section.class, classArgumentCaptor.getValue());
  }
}
