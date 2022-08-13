package com.rjesquivias.todoist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = ImmutableComment.Builder.class)
final class ImmutableComment implements Comment {
    private final long id;

    private final long taskId;

    private final long projectId;

    private final String posted;

    private final String content;

    private ImmutableComment(Builder builder) {
        id = builder.id;
        taskId = builder.taskId;
        projectId = builder.projectId;
        posted = builder.posted;
        content = builder.content;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final ImmutableComment other = (ImmutableComment) obj;

        if (this.id != other.id) {
            return false;
        }
        if (this.taskId != other.taskId) {
            return false;
        }
        if (this.projectId != other.projectId) {
            return false;
        }
        boolean postedEquals = ((this.posted == null && other.posted == null) || (this.posted != null && this.posted.equals(other.posted)));
        if (!postedEquals) {
            return false;
        }
        boolean contentEquals = ((this.content == null && other.content == null) || (this.content != null && this.content.equals(other.content)));
        if (!contentEquals) {
            return false;
        }

        return true;
    }

    @Override
    public long id() {
        return id;
    }

    @Override
    public long taskId() {
        return taskId;
    }

    @Override
    public long projectId() {
        return projectId;
    }

    @Override
    public String posted() {
        return posted;
    }

    @Override
    public String content() {
        return content;
    }

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder {
        private long id;

        private long taskId;

        private long projectId;

        private String posted;

        private String content;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        @JsonProperty("task_id")
        public Builder taskId(long taskId) {
            this.taskId = taskId;
            return this;
        }

        @JsonProperty("project_id")
        public Builder projectId(long projectId) {
            this.projectId = projectId;
            return this;
        }

        public Builder posted(String posted) {
            this.posted = posted;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public ImmutableComment build() {
            return new ImmutableComment(this);
        }
    }
}
