package com.rjesquivias.todoist.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rjesquivias.todoist.domain.Project;
import com.rjesquivias.todoist.util.http.HttpRequestFactory;
import com.rjesquivias.todoist.util.http.HttpRequestHelper;
import io.github.cdimascio.dotenv.Dotenv;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.Collection;
import java.util.logging.Logger;
import org.apache.http.HttpStatus;

public class ProjectDao implements IProjectDao {

  private final String baseUri;
  private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
  private final HttpRequestFactory httpRequestFactory;
  private final HttpRequestHelper httpRequestHelper;

  public ProjectDao(HttpClient client, Dotenv dotenv) {
    this.baseUri = dotenv.get("PROJECT_URI");
    this.httpRequestFactory = new HttpRequestFactory(dotenv.get("TODOIST_API_TOKEN"),
        new ObjectMapper());
    this.httpRequestHelper = new HttpRequestHelper(client);
  }

  @Override
  public Collection<Project> getAll() {
    LOGGER.info("ProjectDao::getAll()");
    HttpRequest request = httpRequestFactory.buildGet(baseUri);
    return httpRequestHelper.makeCollectionRequest(request,
        response -> response.statusCode() == HttpStatus.SC_OK, Project.class);
  }

  @Override
  public Project get(long id) {
    LOGGER.info("ProjectDao::get(long id)");
    HttpRequest request = httpRequestFactory.buildGet(baseUri + id);
    return httpRequestHelper.makeRequest(request,
        response -> response.statusCode() == HttpStatus.SC_OK, Project.class);
  }

  @Override
  public Project create(CreateArgs args) {
    LOGGER.info("ProjectDao::create(CreateArgs args)");
    HttpRequest request = httpRequestFactory.buildPost(baseUri, args);
    return httpRequestHelper.makeRequest(request,
        response -> response.statusCode() == HttpStatus.SC_OK, Project.class);
  }

  @Override
  public void update(long id, UpdateArgs args) {
    LOGGER.info("ProjectDao::update(UpdateArgs args)");
    HttpRequest request = httpRequestFactory.buildPost(baseUri + id, args);
    httpRequestHelper.makeRequest(request,
        response -> response.statusCode() == HttpStatus.SC_NO_CONTENT, Project.class);
  }

  @Override
  public void delete(long id) {
    LOGGER.info("ProjectDao::delete(long id)");
    HttpRequest request = httpRequestFactory.buildDelete(baseUri + id);
    httpRequestHelper.makeRequest(request,
        response -> response.statusCode() == HttpStatus.SC_NO_CONTENT, Project.class);
  }
}
