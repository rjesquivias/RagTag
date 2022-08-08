package com.rjesquivias.todoist.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rjesquivias.todoist.domain.Task;
import com.rjesquivias.todoist.util.http.HttpRequestFactory;
import com.rjesquivias.todoist.util.http.HttpRequestHelper;
import io.github.cdimascio.dotenv.Dotenv;
import java.net.http.HttpRequest;
import java.util.Collection;
import java.util.logging.Logger;

public class TaskDao implements ITaskDao {

  private final String baseUri;
  private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
  private final HttpRequestFactory httpRequestFactory;
  private final HttpRequestHelper httpRequestHelper;

  public TaskDao(HttpRequestHelper httpRequestHelper, Dotenv dotenv) {
    this.baseUri = dotenv.get("TASK_URI");
    this.httpRequestFactory = new HttpRequestFactory(dotenv.get("TODOIST_API_TOKEN"),
        new ObjectMapper());
    this.httpRequestHelper = httpRequestHelper;
  }

  @Override
  public Collection<Task> getAllActive(GetAllActiveArgs args) {
    LOGGER.info("TaskDao::getAllActive()");
    HttpRequest request = httpRequestFactory.buildGet(baseUri);
    return httpRequestHelper.makeCollectionRequest(request, okPredicate, Task.class);
  }

  @Override
  public Task create(CreateArgs args) {
    LOGGER.info("TaskDao::create(CreateArgs args)");
    HttpRequest request = httpRequestFactory.buildPost(baseUri, args);
    return httpRequestHelper.makeRequest(request, okPredicate, Task.class);
  }

  @Override
  public Task getActive(long id) {
    LOGGER.info("TaskDao::get(long id)");
    HttpRequest request = httpRequestFactory.buildGet(baseUri + id);
    return httpRequestHelper.makeRequest(request, okPredicate, Task.class);
  }

  @Override
  public void update(long id, UpdateArgs args) {
    LOGGER.info("TaskDao::update(UpdateArgs args)");
    HttpRequest request = httpRequestFactory.buildPost(baseUri + id, args);
    httpRequestHelper.makeRequest(request, noContentPredicate, Task.class);
  }

  @Override
  public void close(long id) {
    LOGGER.info("TaskDao::close(long id)");
    HttpRequest request = httpRequestFactory.buildPost(baseUri + id + "/close", "");
    httpRequestHelper.makeRequest(request, noContentPredicate, Task.class);
  }

  @Override
  public void reOpen(long id) {
    LOGGER.info("TaskDao::reOpen(long id)");
    HttpRequest request = httpRequestFactory.buildPost(baseUri + id + "/reopen", "");
    httpRequestHelper.makeRequest(request, noContentPredicate, Task.class);
  }

  @Override
  public void delete(long id) {
    LOGGER.info("TaskDao::delete(long id)");
    HttpRequest request = httpRequestFactory.buildDelete(baseUri + id);
    httpRequestHelper.makeRequest(request, noContentPredicate, Task.class);
  }
}
