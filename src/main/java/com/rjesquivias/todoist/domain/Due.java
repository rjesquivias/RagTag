package com.rjesquivias.todoist.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.immutables.value.Value;

@Value.Immutable
@Value.Style(
        builderVisibility = Value.Style.BuilderVisibility.PACKAGE,
        visibility = Value.Style.ImplementationVisibility.PACKAGE,
        overshadowImplementation = true
)
@JsonIgnoreProperties(ignoreUnknown = true)
sealed interface Due permits ImmutableDue {
    String string();

    String date();

    boolean recurring();

    String datetime();

    String timezone();

    static Due.Builder builder() {
        return new Due.Builder();
    }

    final class Builder extends ImmutableDue.Builder {
        Builder() {
        }
    }
}
