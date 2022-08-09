package com.rjesquivias.todoist;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.rjesquivias.todoist.domain.Section;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;

interface ISectionDao {

    Collection<Section> getAll();

    Collection<Section> getAll(long project_id);

    Section create(CreateArgs args);

    Section get(long id);

    void update(long id, String name);

    void delete(long id);

    @Data
    @Builder
    @JsonInclude(Include.NON_NULL)
    class CreateArgs {

        private String name = null;
        private Long project_id = null;
        private Long order = null;
    }
}
