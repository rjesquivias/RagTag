package com.rjesquivias.todoist.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Collection;
import java.util.logging.Logger;
import org.apache.http.HttpStatus;

public class ProjectDao implements IProjectDao {

  private final HttpClient client;
  private final ObjectMapper objectMapper;
  private final String baseUri;
  private final String token;
  private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

  public ProjectDao(HttpClient client, Dotenv dotenv) {
    this.client = client;
    this.token = dotenv.get("TODOIST_API_TOKEN");
    this.baseUri = dotenv.get("PROJECT_URI");
    this.objectMapper = new ObjectMapper();
  }

  @Override
  public Collection<Project> getAll() {
    LOGGER.info("ProjectDao::getAll()");

    try {
      HttpRequest request = HttpRequest.newBuilder().uri(new URI(baseUri))
          .header("Authorization", "Bearer " + token)
          .GET().build();
      HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
      if (response.statusCode() == HttpStatus.SC_OK) {
        return objectMapper.readValue(response.body(),
            new TypeReference<>() {
            });
      } else {
        throw new ServiceUnavailableException(
            String.format("Status: %d Body: %s", response.statusCode(), response.body()));
      }
    } catch (URISyntaxException | IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Project get(long id) {
    LOGGER.info("ProjectDao::get(long id)");
    final String projectUri = baseUri + id;

    try {
      HttpRequest request = HttpRequest.newBuilder().uri(new URI(projectUri))
          .header("Authorization", "Bearer " + token)
          .GET().build();
      HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
      if (response.statusCode() == HttpStatus.SC_OK) {
        return objectMapper.readValue(response.body(), Project.class);
      } else {
        throw new ServiceUnavailableException(
            String.format("Status: %d Body: %s", response.statusCode(), response.body()));
      }
    } catch (URISyntaxException | IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Project create(CreateArgs args) {
    LOGGER.info("ProjectDao::create(CreateArgs args)");

    try {
      HttpRequest request = HttpRequest.newBuilder().uri(new URI(baseUri))
          .header("Authorization", "Bearer " + token)
          .header("Content-Type", "application/json")
          .POST(BodyPublishers.ofString(objectMapper.writeValueAsString(args))).build();
      HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
      if (response.statusCode() == HttpStatus.SC_OK) {
        return objectMapper.readValue(response.body(), Project.class);
      } else {
        throw new ServiceUnavailableException(
            String.format("Status: %d Body: %s", response.statusCode(), response.body()));
      }
    } catch (URISyntaxException | IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void update(long id, UpdateArgs args) {
    LOGGER.info("ProjectDao::update(UpdateArgs args)");
    final String projectUri = baseUri + id;

    try {
      HttpRequest request = HttpRequest.newBuilder().uri(new URI(projectUri))
          .header("Authorization", "Bearer " + token)
          .header("Content-Type", "application/json")
          .POST(BodyPublishers.ofString(objectMapper.writeValueAsString(args))).build();
      HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
      if (response.statusCode() != HttpStatus.SC_NO_CONTENT) {
        throw new ServiceUnavailableException(
            String.format("Status: %d Body: %s", response.statusCode(), response.body()));
      }
    } catch (URISyntaxException | IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void delete(long id) {
    LOGGER.info("ProjectDao::delete(long id)");
    final String projectUri = baseUri + id;

    try {
      HttpRequest request = HttpRequest.newBuilder().uri(new URI(projectUri))
          .header("Authorization", "Bearer " + token)
          .DELETE().build();
      HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
      if (response.statusCode() != HttpStatus.SC_NO_CONTENT) {
        throw new ServiceUnavailableException(
            String.format("Status: %d Body: %s", response.statusCode(), response.body()));
      }
    } catch (URISyntaxException | IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
