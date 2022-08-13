package com.rjesquivias.todoist;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;

public interface Arguments {
    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    final class CreateCommentArgs {

        private Long task_id;
        private Long project_id;
        private String content;
    }

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    class CreateLabelArgs {

        private String name;
        private Long order;
        private Color color;
        private Boolean favorite;
    }

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    class UpdateLabelArgs {

        private String name;
        private Long order;
        private Color color;
        private Boolean favorite;
    }

    @Data
    @Builder
    class CreateProjectArgs {

        private String name;
        private long parent_id;
        private Color color;
        private boolean favorite;
    }

    @Data
    @Builder
    class UpdateProjectArgs {

        private String name;
        private Color color;
        private boolean favorite;
    }

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    class CreateSectionArgs {

        private String name;
        private Long project_id;
        private Long order;
    }

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    class GetAllActiveTasksArgs {

        private Long project_id;
        private Long section_id;
        private Long label_id;
        private String filter;
        private String lang;
        private Collection<Long> ids;
    }

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    class CreateTaskArgs {

        private String content;
        private String description;
        private Long project_id;
        private Long section_id;
        private Long parent_id;
        private Long parent;
        private Long order;
        private Collection<Long> label_ids;
        private Long priority;
        private String due_string;
        private String due_date;
        private String due_datetime;
        private String due_lang;
        private Long assignee;
    }

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    class UpdateTaskArgs {

        private String content;
        private String description;
        private Collection<Long> label_ids;
        private Long priority;
        private String due_string;
        private String due_date;
        private String due_datetime;
        private String due_lang;
        private Long assignee;
    }
}
