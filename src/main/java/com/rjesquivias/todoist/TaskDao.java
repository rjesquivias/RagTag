package com.rjesquivias.todoist;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpRequest;
import java.util.Collection;
import java.util.logging.Logger;

import static com.rjesquivias.todoist.Predicates.noContentPredicate;
import static com.rjesquivias.todoist.Predicates.okPredicate;

final class TaskDao implements ITaskDao {

    public static final String CLOSE = "/close";
    public static final String REOPEN = "/reopen";
    public static final String BODY = "";
    private final String baseUri;
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final HttpRequestFactory httpRequestFactory;
    private final HttpRequestHelper httpRequestHelper;

    public TaskDao(HttpRequestHelper httpRequestHelper, String taskUri, String apiToken) {
        this.baseUri = taskUri;
        this.httpRequestFactory = new HttpRequestFactory(apiToken,
                new ObjectMapper());
        this.httpRequestHelper = httpRequestHelper;
    }

    @Override
    public Collection<Task> getAllActive(Arguments.GetAllActiveTasksArgs args) {
        LOGGER.info("TaskDao::getAllActive()");
        HttpRequest request = httpRequestFactory.buildGet(baseUri);
        return httpRequestHelper.makeCollectionRequest(request, okPredicate, ImmutableTask.class);
    }

    @Override
    public Task create(Arguments.CreateTaskArgs args) {
        LOGGER.info("TaskDao::create(CreateArgs args)");
        HttpRequest request = httpRequestFactory.buildPost(baseUri, args);
        return httpRequestHelper.makeRequest(request, okPredicate, ImmutableTask.class);
    }

    @Override
    public Task getActive(long id) {
        LOGGER.info("TaskDao::get(long id)");
        HttpRequest request = httpRequestFactory.buildGet(baseUri + id);
        return httpRequestHelper.makeRequest(request, okPredicate, ImmutableTask.class);
    }

    @Override
    public void update(long id, Arguments.UpdateTaskArgs args) {
        LOGGER.info("TaskDao::update(UpdateArgs args)");
        HttpRequest request = httpRequestFactory.buildPost(baseUri + id, args);
        httpRequestHelper.makeRequest(request, noContentPredicate, ImmutableTask.class);
    }

    @Override
    public void close(long id) {
        LOGGER.info("TaskDao::close(long id)");
        HttpRequest request = httpRequestFactory.buildPost(baseUri + id + CLOSE, BODY);
        httpRequestHelper.makeRequest(request, noContentPredicate, ImmutableTask.class);
    }

    @Override
    public void reOpen(long id) {
        LOGGER.info("TaskDao::reOpen(long id)");
        HttpRequest request = httpRequestFactory.buildPost(baseUri + id + REOPEN, BODY);
        httpRequestHelper.makeRequest(request, noContentPredicate, ImmutableTask.class);
    }

    @Override
    public void delete(long id) {
        LOGGER.info("TaskDao::delete(long id)");
        HttpRequest request = httpRequestFactory.buildDelete(baseUri + id);
        httpRequestHelper.makeRequest(request, noContentPredicate, ImmutableTask.class);
    }
}
