package com.rjesquivias.todoist.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.immutables.value.Value;

@Value.Immutable
@Value.Style(
        builderVisibility = Value.Style.BuilderVisibility.PACKAGE,
        visibility = Value.Style.ImplementationVisibility.PACKAGE,
        overshadowImplementation = true
)
@JsonIgnoreProperties(ignoreUnknown = true)
public sealed interface Project permits ImmutableProject {
    @JsonProperty("id")
    long id();

    @JsonProperty("name")
    String name();

    @JsonProperty("color")
    Color color();

    @JsonProperty("parent_id")
    long parentId();

    @JsonProperty("order")
    long order();

    @JsonProperty("comment_count")
    long commentCount();

    @JsonProperty("shared")
    boolean shared();

    @JsonProperty("favorite")
    boolean favorite();

    @JsonProperty("inbox_project")
    boolean inboxProject();

    @JsonProperty("team_inbox")
    boolean teamInbox();

    @JsonProperty("sync_id")
    long syncId();

    @JsonProperty("url")
    String url();

    static Builder builder() {
        return new Builder();
    }

    final class Builder extends ImmutableProject.Builder {
        Builder() {
        }
    }
}
