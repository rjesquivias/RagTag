package com.rjesquivias.todoist;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpRequest;
import java.util.Collection;
import java.util.logging.Logger;

import static com.rjesquivias.todoist.Predicates.noContentPredicate;
import static com.rjesquivias.todoist.Predicates.okPredicate;

final class LabelDao implements ILabelDao {

    private final String baseUri;
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final HttpRequestFactory httpRequestFactory;
    private final HttpRequestHelper httpRequestHelper;

    public LabelDao(HttpRequestHelper httpRequestHelper, String labelUri, String apiToken) {
        this.baseUri = labelUri;
        this.httpRequestFactory = new HttpRequestFactory(apiToken,
                new ObjectMapper());
        this.httpRequestHelper = httpRequestHelper;
    }

    @Override
    public Collection<Label> getAll() {
        LOGGER.info("LabelDao::getAll()");
        HttpRequest request = httpRequestFactory.buildGet(baseUri);
        return httpRequestHelper.makeCollectionRequest(request, okPredicate, ImmutableLabel.class);
    }

    @Override
    public Label create(Arguments.CreateLabelArgs args) {
        LOGGER.info("LabelDao::create(CreateArgs args)");
        HttpRequest request = httpRequestFactory.buildPost(baseUri, args);
        return httpRequestHelper.makeRequest(request, okPredicate, ImmutableLabel.class);
    }

    @Override
    public Label get(long id) {
        LOGGER.info("LabelDao::get(long id)");
        HttpRequest request = httpRequestFactory.buildGet(baseUri + id);
        return httpRequestHelper.makeRequest(request, okPredicate, ImmutableLabel.class);
    }

    @Override
    public void update(long id, Arguments.UpdateLabelArgs args) {
        LOGGER.info("LabelDao::update(long id, UpdateArgs args)");
        HttpRequest request = httpRequestFactory.buildPost(baseUri + id, args);
        httpRequestHelper.makeRequest(request, noContentPredicate, ImmutableLabel.class);
    }

    @Override
    public void delete(long id) {
        LOGGER.info("LabelDao::delete(long id)");
        HttpRequest request = httpRequestFactory.buildDelete(baseUri + id);
        httpRequestHelper.makeRequest(request, noContentPredicate, ImmutableLabel.class);
    }
}
