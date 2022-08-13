package com.rjesquivias.todoist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = ImmutableLabel.Builder.class)
final class ImmutableLabel implements Label {
    private final long id;

    private final String name;

    private final Color color;

    private final long order;

    private final boolean favorite;

    private ImmutableLabel(Builder builder) {
        id = builder.id;
        name = builder.name;
        color = builder.color;
        order = builder.order;
        favorite = builder.favorite;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final ImmutableLabel other = (ImmutableLabel) obj;

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
        if (this.order != other.order) {
            return false;
        }
        if (this.favorite != other.favorite) {
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
    public long order() {
        return order;
    }

    @Override
    public boolean favorite() {
        return favorite;
    }

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder {
        private long id;

        private String name;

        private Color color;

        private long order;

        private boolean favorite;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder color(Color color) {
            this.color = color;
            return this;
        }

        public Builder order(long order) {
            this.order = order;
            return this;
        }

        public Builder favorite(boolean favorite) {
            this.favorite = favorite;
            return this;
        }

        public ImmutableLabel build() {
            return new ImmutableLabel(this);
        }
    }
}
