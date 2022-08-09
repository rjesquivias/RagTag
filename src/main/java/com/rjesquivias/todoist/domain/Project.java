package com.rjesquivias.todoist.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.immutables.value.Value;

@Value.Immutable
@Value.Style(
        builderVisibility = Value.Style.BuilderVisibility.PACKAGE,
        visibility = Value.Style.ImplementationVisibility.PACKAGE,
        overshadowImplementation = true
)
@JsonIgnoreProperties(ignoreUnknown = true)
public sealed interface Project permits ImmutableProject {
  long id();
  String name();
  Color color();
  @JsonProperty("parent_id")
  long parentId();
  long order();
  @JsonProperty("comment_count")
  long commentCount();
  boolean shared();
  boolean favorite();
  @JsonProperty("inbox_project")
  boolean inboxProject();
  @JsonProperty("team_inbox")
  boolean teamInbox();
  @JsonProperty("sync_id")
  long syncId();
  String url();

  static Builder builder() {
    return new Builder();
  }

  final class Builder extends ImmutableProject.Builder {
    Builder() {}
  }
}
