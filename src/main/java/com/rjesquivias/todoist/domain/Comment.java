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
public sealed interface Comment permits ImmutableComment {
  long id();
  @JsonProperty("task_id")
  long taskId();
  @JsonProperty("project_id")
  long projectId();
  String posted();
  String content();

  static Builder builder() {
    return new Builder();
  }

  final class Builder extends ImmutableComment.Builder {
    Builder() {}
  }
}

