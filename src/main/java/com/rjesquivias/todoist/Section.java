package com.rjesquivias.todoist;

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
public sealed interface Section permits ImmutableSection {
    long id();

    @JsonProperty("project_id")
    long projectId();

    long order();

    String name();

    static Builder builder() {
        return new Builder();
    }

    final class Builder extends ImmutableSection.Builder {
        Builder() {
        }
    }
}