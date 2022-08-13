package com.rjesquivias.todoist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = ImmutableSection.Builder.class)
final class ImmutableSection implements Section {
    private final long id;
    private final long projectId;
    private final long order;
    private final String name;

    private ImmutableSection(Builder builder) {
        id = builder.id;
        projectId = builder.projectId;
        order = builder.order;
        name = builder.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final ImmutableSection other = (ImmutableSection) obj;

        if (this.id != other.id) {
            return false;
        }
        if (this.projectId != other.projectId) {
            return false;
        }
        if (this.order != other.order) {
            return false;
        }
        boolean nameEquals = ((this.name == null && other.name == null) || (this.name != null && this.name.equals(other.name)));
        if (!nameEquals) {
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
    public long order() {
        return order;
    }

    @Override
    public String name() {
        return name;
    }

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder {
        private long id;
        private long projectId;
        private long order;
        private String name;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        @JsonProperty("project_id")
        public Builder projectId(long projectId) {
            this.projectId = projectId;
            return this;
        }

        public Builder order(long order) {
            this.order = order;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public ImmutableSection build() {
            return new ImmutableSection(this);
        }
    }
}
