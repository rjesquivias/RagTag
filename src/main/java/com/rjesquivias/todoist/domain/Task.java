package com.rjesquivias.todoist.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.immutables.value.Value;

import java.util.Collection;

@Value.Immutable
@Value.Style(
        builderVisibility = Value.Style.BuilderVisibility.PACKAGE,
        visibility = Value.Style.ImplementationVisibility.PACKAGE,
        overshadowImplementation = true
)
@JsonIgnoreProperties(ignoreUnknown = true)
public sealed interface Task permits ImmutableTask {

    long id();

    @JsonProperty("project_id")
    long projectId();

    @JsonProperty("section_id")
    long sectionId();

    String content();

    String description();

    boolean completed();

    @JsonProperty("label_ids")
    Collection<Long> labelIds();

    @JsonProperty("parent_id")
    long parentId();

    long order();

    long priority();

    Due due();

    String url();

    @JsonProperty("comment_count")
    long commentCount();

    long assignee();

    long assigner();

    static Task.Builder builder() {
        return new Task.Builder();
    }

    final class Builder extends ImmutableTask.Builder {
        Builder() {
        }
    }
}
