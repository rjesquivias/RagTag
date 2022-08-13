package com.rjesquivias.todoist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.immutables.value.Value;


@Value.Immutable
@Value.Style(
        builderVisibility = Value.Style.BuilderVisibility.PACKAGE,
        visibility = Value.Style.ImplementationVisibility.PACKAGE,
        overshadowImplementation = true
)
@JsonIgnoreProperties(ignoreUnknown = true)
public sealed interface Label permits ImmutableLabel {
    long id();

    String name();

    Color color();

    long order();

    boolean favorite();

    static Builder builder() {
        return new Builder();
    }

    final class Builder extends ImmutableLabel.Builder {
        Builder() {
        }
    }
}
