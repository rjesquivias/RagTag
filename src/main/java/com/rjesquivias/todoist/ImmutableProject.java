package com.rjesquivias.todoist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = ImmutableProject.Builder.class)
final class ImmutableProject implements Project {
    private final long id;

    private final String name;

    private final Color color;

    private final long parentId;

    private final long order;

    private final long commentCount;

    private final boolean shared;

    private final boolean favorite;

    private final boolean inboxProject;

    private final boolean teamInbox;

    private final long syncId;

    private final String url;

    private ImmutableProject(Builder builder) {
        id = builder.id;
        name = builder.name;
        color = builder.color;
        parentId = builder.parentId;
        order = builder.order;
        commentCount = builder.commentCount;
        shared = builder.shared;
        favorite = builder.favorite;
        inboxProject = builder.inboxProject;
        teamInbox = builder.teamInbox;
        syncId = builder.syncId;
        url = builder.url;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final ImmutableProject other = (ImmutableProject) obj;

        if (this.id != other.id) {
            return false;
        }
        boolean nameEquals = ((this.name == null && other.name == null) || (this.name != null && this.name.equals(other.name)));
        if (!nameEquals) {
            return false;
        }
        if (this.color != other.color) {
            return false;
        }
        if (this.parentId != other.parentId) {
            return false;
        }
        if (this.order != other.order) {
            return false;
        }
        if (this.commentCount != other.commentCount) {
            return false;
        }
        if (this.shared != other.shared) {
            return false;
        }
        if (this.favorite != other.favorite) {
            return false;
        }
        if (this.inboxProject != other.inboxProject) {
            return false;
        }
        if (this.teamInbox != other.teamInbox) {
            return false;
        }
        if (this.syncId != other.syncId) {
            return false;
        }
        boolean urlEquals = ((this.url == null && other.url == null) || (this.url != null && this.url.equals(other.url)));
        if (!urlEquals) {
            return false;
        }

        return true;
    }

    @Override
    public long id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Color color() {
        return color;
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
    public long commentCount() {
        return commentCount;
    }

    @Override
    public boolean shared() {
        return shared;
    }

    @Override
    public boolean favorite() {
        return favorite;
    }

    @Override
    public boolean inboxProject() {
        return inboxProject;
    }

    @Override
    public boolean teamInbox() {
        return teamInbox;
    }

    @Override
    public long syncId() {
        return syncId;
    }

    @Override
    public String url() {
        return url;
    }

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder {
        private long id;

        private String name;

        private Color color;

        private long parentId;

        private long order;

        private long commentCount;

        private boolean shared;

        private boolean favorite;

        private boolean inboxProject;

        private boolean teamInbox;

        private long syncId;

        private String url;

        @JsonProperty("id")
        public Builder id(long id) {
            this.id = id;
            return this;
        }

        @JsonProperty("name")
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        @JsonProperty("color")
        public Builder color(Color color) {
            this.color = color;
            return this;
        }

        @JsonProperty("parent_id")
        public Builder parentId(long parentId) {
            this.parentId = parentId;
            return this;
        }

        @JsonProperty("order")
        public Builder order(long order) {
            this.order = order;
            return this;
        }

        @JsonProperty("comment_count")
        public Builder commentCount(long commentCount) {
            this.commentCount = commentCount;
            return this;
        }

        @JsonProperty("shared")
        public Builder shared(boolean shared) {
            this.shared = shared;
            return this;
        }

        @JsonProperty("favorite")
        public Builder favorite(boolean favorite) {
            this.favorite = favorite;
            return this;
        }

        @JsonProperty("inbox_project")
        public Builder inboxProject(boolean inboxProject) {
            this.inboxProject = inboxProject;
            return this;
        }

        @JsonProperty("team_inbox")
        public Builder teamInbox(boolean teamInbox) {
            this.teamInbox = teamInbox;
            return this;
        }

        @JsonProperty("sync_id")
        public Builder syncId(long syncId) {
            this.syncId = syncId;
            return this;
        }

        @JsonProperty("url")
        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public ImmutableProject build() {
            return new ImmutableProject(this);
        }
    }
}
