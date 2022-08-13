package com.rjesquivias.todoist;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpRequest;
import java.util.Collection;
import java.util.logging.Logger;

import static com.rjesquivias.todoist.Predicates.noContentPredicate;
import static com.rjesquivias.todoist.Predicates.okPredicate;

final class SectionDao implements ISectionDao {

    private final String baseUri;
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final HttpRequestFactory httpRequestFactory;
    private final HttpRequestHelper httpRequestHelper;

    public SectionDao(HttpRequestHelper httpRequestHelper, String sectionUri, String apiToken) {
        this.baseUri = sectionUri;
        this.httpRequestFactory = new HttpRequestFactory(apiToken,
                new ObjectMapper());
        this.httpRequestHelper = httpRequestHelper;
    }

    @Override
    public Collection<Section> getAll() {
        LOGGER.info("SectionDao::getAll()");
        HttpRequest request = httpRequestFactory.buildGet(baseUri);
        return httpRequestHelper.makeCollectionRequest(request, okPredicate, ImmutableSection.class);
    }

    @Override
    public Collection<Section> getAll(long project_id) {
        LOGGER.info("SectionDao::getAll(long project_id)");
        HttpRequest request = httpRequestFactory.buildGet(
                String.format("%s?project_id=%d", baseUri, project_id));
        return httpRequestHelper.makeCollectionRequest(request, okPredicate, ImmutableSection.class);
    }

    @Override
    public Section create(Arguments.CreateSectionArgs args) {
        LOGGER.info("SectionDao::create(CreateArgs args)");
        HttpRequest request = httpRequestFactory.buildPost(baseUri, args);
        return httpRequestHelper.makeRequest(request, okPredicate, ImmutableSection.class);
    }

    @Override
    public Section get(long id) {
        LOGGER.info("SectionDao::get(long id)");
        HttpRequest request = httpRequestFactory.buildGet(baseUri + id);
        return httpRequestHelper.makeRequest(request, okPredicate, ImmutableSection.class);
    }

    @Override
    public void update(long id, String name) {
        LOGGER.info("SectionDao::update(long id, String name)");
        HttpRequest request = httpRequestFactory.buildPost(baseUri + id,
                String.format("{\"name\": \"%s\"}", name));
        httpRequestHelper.makeRequest(request, noContentPredicate, ImmutableSection.class);
    }

    @Override
    public void delete(long id) {
        LOGGER.info("SectionDao::delete(long id)");
        HttpRequest request = httpRequestFactory.buildDelete(baseUri + id);
        httpRequestHelper.makeRequest(request, noContentPredicate, ImmutableSection.class);
    }
}
