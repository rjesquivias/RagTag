package com.rjesquivias.todoist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rjesquivias.todoist.domain.ImmutableProject;
import com.rjesquivias.todoist.domain.Project;
import org.apache.http.HttpStatus;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.Collection;
import java.util.logging.Logger;

import static com.rjesquivias.todoist.Predicates.noContentPredicate;
import static com.rjesquivias.todoist.Predicates.okPredicate;

final class ProjectDao implements IProjectDao {

    private final String baseUri;
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final HttpRequestFactory httpRequestFactory;
    private final HttpRequestHelper httpRequestHelper;

    public ProjectDao(HttpRequestHelper httpRequestHelper, String projectUri, String apiToken) {
        this.baseUri = projectUri;
        this.httpRequestFactory = new HttpRequestFactory(apiToken,
                new ObjectMapper());
        this.httpRequestHelper = httpRequestHelper;
    }

    @Override
    public Collection<Project> getAll() {
        LOGGER.info("ProjectDao::getAll()");
        HttpRequest request = httpRequestFactory.buildGet(baseUri);
        return httpRequestHelper.makeCollectionRequest(request, okPredicate, ImmutableProject.class);
    }

    @Override
    public Project get(long id) {
        LOGGER.info("ProjectDao::get(long id)");
        HttpRequest request = httpRequestFactory.buildGet(baseUri + id);
        return httpRequestHelper.makeRequest(request, okPredicate, ImmutableProject.class);
    }

    @Override
    public Project create(CreateArgs args) {
        LOGGER.info("ProjectDao::create(CreateArgs args)");
        HttpRequest request = httpRequestFactory.buildPost(baseUri, args);
        return httpRequestHelper.makeRequest(request, okPredicate, ImmutableProject.class);
    }

    @Override
    public void update(long id, UpdateArgs args) {
        LOGGER.info("ProjectDao::update(UpdateArgs args)");
        HttpRequest request = httpRequestFactory.buildPost(baseUri + id, args);
        httpRequestHelper.makeRequest(request, noContentPredicate, ImmutableProject.class);
    }

    @Override
    public void delete(long id) {
        LOGGER.info("ProjectDao::delete(long id)");
        HttpRequest request = httpRequestFactory.buildDelete(baseUri + id);
        httpRequestHelper.makeRequest(request, noContentPredicate, ImmutableProject.class);
    }
}
