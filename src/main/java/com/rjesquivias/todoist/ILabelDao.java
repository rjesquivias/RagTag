package com.rjesquivias.todoist;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.rjesquivias.todoist.domain.Color;
import com.rjesquivias.todoist.domain.Label;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;

interface ILabelDao {

    Collection<Label> getAll();

    Label create(CreateArgs args);

    @Data
    @Builder
    @JsonInclude(Include.NON_NULL)
    class CreateArgs {

        private String name = null;
        private Long order = null;
        private Color color = null;
        private Boolean favorite = null;
    }

    Label get(long id);

    void update(long id, UpdateArgs args);

    @Data
    @Builder
    @JsonInclude(Include.NON_NULL)
    class UpdateArgs {

        private String name = null;
        private Long order = null;
        private Color color = null;
        private Boolean favorite = null;
    }

    void delete(long id);
}
