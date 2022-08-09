package com.rjesquivias.todoist;

import com.rjesquivias.todoist.domain.Color;
import com.rjesquivias.todoist.domain.Project;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;

interface IProjectDao {

    Collection<Project> getAll();

    Project get(long id);

    Project create(CreateArgs args);

    void update(long id, UpdateArgs args);

    void delete(long id);

    @Data
    @Builder
    class CreateArgs {

        private String name;
        private long parent_id;
        private long color;
        private boolean favorite;
    }

    @Data
    @Builder
    class UpdateArgs {

        private String name;
        private Color color;
        private boolean favorite;
    }
}
