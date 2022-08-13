package com.rjesquivias.todoist.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.Collection;

@JsonDeserialize(builder = ImmutableTask.Builder.class)
public final class ImmutableTask implements Task {

    private final long id;

    private final long projectId;

    private final long sectionId;

    private final String content;

    private final String description;

    private final boolean completed;

    private final Collection<Long> labelIds;

    private final long parentId;

    private final long order;

    private final long priority;

    private final ImmutableDue due;

    private final String url;

    private final long commentCount;

    private final long assignee;

    private final long assigner;

    private ImmutableTask(Builder builder) {
        id = builder.id;
        projectId = builder.projectId;
        sectionId = builder.sectionId;
        content = builder.content;
        description = builder.description;
        completed = builder.completed;
        labelIds = builder.labelIds;
        parentId = builder.parentId;
        order = builder.order;
        priority = builder.priority;
        due = builder.due;
        url = builder.url;
        commentCount = builder.commentCount;
        assignee = builder.assignee;
        assigner = builder.assigner;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final ImmutableTask other = (ImmutableTask) obj;


        if (this.id != other.id) {
            return false;
        }
        if (this.projectId != other.projectId) {
            return false;
        }
        if (this.sectionId != other.sectionId) {
            return false;
        }
        boolean contentEquals = ((this.content == null && other.content == null) || (this.content != null && this.content.equals(other.content)));
        if (!contentEquals) {
            return false;
        }
        boolean descriptionEquals = ((this.description == null && other.description == null) || (this.description != null && this.description.equals(other.description)));
        if (!descriptionEquals) {
            return false;
        }
        if (this.completed != other.completed) {
            return false;
        }
        boolean labelIdsEquals = ((this.labelIds == null && other.labelIds == null) || ((this.labelIds != null && (this.labelIds.containsAll(other.labelIds)) || (other.labelIds != null && other.labelIds.containsAll(this.labelIds)))));
        if (!labelIdsEquals) {
            return false;
        }
        if (this.parentId != other.parentId) {
            return false;
        }
        if (this.order != other.order) {
            return false;
        }
        if (this.priority != other.priority) {
            return false;
        }
        boolean dueEquals = ((this.due == null && other.due == null) || (this.due != null && this.due.equals(other.due)));
        if (!dueEquals) {
            return false;
        }
        boolean urlEquals = ((this.url == null && other.url == null) || (this.url != null && this.url.equals(other.url)));
        if (!urlEquals) {
            return false;
        }
        if (this.commentCount != other.commentCount) {
            return false;
        }
        if (this.assignee != other.assignee) {
            return false;
        }
        if (this.assigner != other.assigner) {
            return false;
        }

        return true;
    }


    @Override
    public long id() {
        return id;
    }

    @Override
    public long projectId() {
        return projectId;
    }

    @Override
    public long sectionId() {
        return sectionId;
    }

    @Override
    public String content() {
        return content;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public boolean completed() {
        return completed;
    }

    @Override
    public Collection<Long> labelIds() {
        return labelIds;
    }

    @Override
    public long parentId() {
        return parentId;
    }

    @Override
    public long order() {
        return order;
    }

    @Override
    public long priority() {
        return priority;
    }

    @Override
    public ImmutableDue due() {
        return due;
    }

    @Override
    public String url() {
        return url;
    }

    @Override
    public long commentCount() {
        return commentCount;
    }

    @Override
    public long assignee() {
        return assignee;
    }

    @Override
    public long assigner() {
        return assigner;
    }

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder {
        private long id;

        private long projectId;

        private long sectionId;

        private String content;

        private String description;

        private boolean completed;

        private Collection<Long> labelIds;

        private long parentId;

        private long order;

        private long priority;

        private ImmutableDue due;

        private String url;

        private long commentCount;

        private long assignee;

        private long assigner;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        @JsonProperty("project_id")
        public Builder projectId(long projectId) {
            this.projectId = projectId;
            return this;
        }

        @JsonProperty("section_id")
        public Builder sectionId(long sectionId) {
            this.sectionId = sectionId;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder completed(boolean completed) {
            this.completed = completed;
            return this;
        }

        @JsonProperty("label_ids")
        public Builder labelIds(Collection<Long> labelIds) {
            this.labelIds = labelIds;
            return this;
        }

        @JsonProperty("parent_id")
        public Builder parentId(long parentId) {
            this.parentId = parentId;
            return this;
        }

        public Builder order(long order) {
            this.order = order;
            return this;
        }

        public Builder priority(long priority) {
            this.priority = priority;
            return this;
        }

        public Builder due(ImmutableDue due) {
            this.due = due;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        @JsonProperty("comment_count")
        public Builder commentCount(long commentCount) {
            this.commentCount = commentCount;
            return this;
        }

        public Builder assignee(long assignee) {
            this.assignee = assignee;
            return this;
        }

        public Builder assigner(long assigner) {
            this.assigner = assigner;
            return this;
        }

        public ImmutableTask build() {
            return new ImmutableTask(this);
        }
    }
}
