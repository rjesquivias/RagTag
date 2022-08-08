package com.rjesquivias.todoist.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rjesquivias.todoist.domain.Label;
import com.rjesquivias.todoist.util.http.HttpRequestFactory;
import com.rjesquivias.todoist.util.http.HttpRequestHelper;
import io.github.cdimascio.dotenv.Dotenv;
import java.net.http.HttpRequest;
import java.util.Collection;
import java.util.logging.Logger;

public class LabelDao implements ILabelDao {

  private final String baseUri;
  private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
  private final HttpRequestFactory httpRequestFactory;
  private final HttpRequestHelper httpRequestHelper;

  public LabelDao(HttpRequestHelper httpRequestHelper, Dotenv dotenv) {
    this.baseUri = dotenv.get("LABEL_URI");
    this.httpRequestFactory = new HttpRequestFactory(dotenv.get("TODOIST_API_TOKEN"),
        new ObjectMapper());
    this.httpRequestHelper = httpRequestHelper;
  }

  @Override
  public Collection<Label> getAll() {
    LOGGER.info("LabelDao::getAll()");
    HttpRequest request = httpRequestFactory.buildGet(baseUri);
    return httpRequestHelper.makeCollectionRequest(request, okPredicate, Label.class);
  }

  @Override
  public Label create(CreateArgs args) {
    LOGGER.info("LabelDao::create(CreateArgs args)");
    HttpRequest request = httpRequestFactory.buildPost(baseUri, args);
    return httpRequestHelper.makeRequest(request, okPredicate, Label.class);
  }

  @Override
  public Label get(long id) {
    LOGGER.info("LabelDao::get(long id)");
    HttpRequest request = httpRequestFactory.buildGet(baseUri + id);
    return httpRequestHelper.makeRequest(request, okPredicate, Label.class);
  }

  @Override
  public void update(long id, UpdateArgs args) {
    LOGGER.info("LabelDao::update(long id, UpdateArgs args)");
    HttpRequest request = httpRequestFactory.buildPost(baseUri + id, args);
    httpRequestHelper.makeRequest(request, noContentPredicate, Label.class);
  }

  @Override
  public void delete(long id) {
    LOGGER.info("LabelDao::delete(long id)");
    HttpRequest request = httpRequestFactory.buildDelete(baseUri + id);
    httpRequestHelper.makeRequest(request, noContentPredicate, Label.class);
  }
}
