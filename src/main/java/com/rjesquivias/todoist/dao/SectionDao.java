package com.rjesquivias.todoist.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rjesquivias.todoist.domain.Section;
import com.rjesquivias.todoist.util.http.HttpRequestFactory;
import com.rjesquivias.todoist.util.http.HttpRequestHelper;
import com.rjesquivias.todoist.util.http.ResponsePredicate;
import io.github.cdimascio.dotenv.Dotenv;
import java.net.http.HttpRequest;
import java.util.Collection;
import java.util.logging.Logger;
import org.apache.http.HttpStatus;

public class SectionDao implements ISectionDao {

  private final String baseUri;
  private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
  private final HttpRequestFactory httpRequestFactory;
  private final HttpRequestHelper httpRequestHelper;
  final static ResponsePredicate okPredicate = response ->
      response.statusCode() == HttpStatus.SC_OK;
  final static ResponsePredicate noContentPredicate = response ->
      response.statusCode() == HttpStatus.SC_NO_CONTENT;

  public SectionDao(HttpRequestHelper httpRequestHelper, Dotenv dotenv) {
    this.baseUri = dotenv.get("SECTION_URI");
    this.httpRequestFactory = new HttpRequestFactory(dotenv.get("TODOIST_API_TOKEN"),
        new ObjectMapper());
    this.httpRequestHelper = httpRequestHelper;
  }

  @Override
  public Collection<Section> getAll() {
    LOGGER.info("SectionDao::getAll()");
    HttpRequest request = httpRequestFactory.buildGet(baseUri);
    return httpRequestHelper.makeCollectionRequest(request, okPredicate, Section.class);
  }

  @Override
  public Collection<Section> getAll(long project_id) {
    LOGGER.info("SectionDao::getAll()");
    HttpRequest request = httpRequestFactory.buildGet(
        String.format("%s?project_id=%d", baseUri, project_id));
    return httpRequestHelper.makeCollectionRequest(request, okPredicate, Section.class);
  }

  @Override
  public Section create(CreateArgs args) {
    LOGGER.info("SectionDao::create(CreateArgs args)");
    HttpRequest request = httpRequestFactory.buildPost(baseUri, args);
    return httpRequestHelper.makeRequest(request, okPredicate, Section.class);
  }

  @Override
  public Section get(long id) {
    LOGGER.info("SectionDao::get(long id)");
    HttpRequest request = httpRequestFactory.buildGet(baseUri + id);
    return httpRequestHelper.makeRequest(request, okPredicate, Section.class);
  }

  @Override
  public void update(long id, String name) {
    LOGGER.info("SectionDao::update(UpdateArgs args)");
    HttpRequest request = httpRequestFactory.buildPost(baseUri + id,
        String.format("{\"name\": \"%s\"}", name));
    httpRequestHelper.makeRequest(request, noContentPredicate, Section.class);
  }

  @Override
  public void delete(long id) {
    LOGGER.info("SectionDao::delete(long id)");
    HttpRequest request = httpRequestFactory.buildDelete(baseUri + id);
    httpRequestHelper.makeRequest(request, noContentPredicate, Section.class);
  }
}
