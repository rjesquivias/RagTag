package com.rjesquivias.todoist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = ImmutableDue.Builder.class)
final class ImmutableDue implements Due {

    private final String string;
    private final String date;
    private final boolean recurring;
    private final String datetime;
    private final String timezone;

    private ImmutableDue(Builder builder) {
        string = builder.string;
        date = builder.date;
        recurring = builder.recurring;
        datetime = builder.datetime;
        timezone = builder.timezone;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final ImmutableDue other = (ImmutableDue) obj;

        boolean stringEquals = ((this.string == null && other.string == null) || (this.string != null && this.string.equals(other.string)));
        if (!stringEquals) {
            return false;
        }
        boolean dateEquals = ((this.date == null && other.date == null) || (this.date != null && this.date.equals(other.date)));
        if (!dateEquals) {
            return false;
        }
        if (this.recurring != other.recurring) {
            return false;
        }
        boolean datetimeEquals = ((this.datetime == null && other.datetime == null) || (this.datetime != null && this.datetime.equals(other.datetime)));
        if (!datetimeEquals) {
            return false;
        }
        boolean timezoneEquals = ((this.timezone == null && other.timezone == null) || (this.timezone != null && this.timezone.equals(other.timezone)));
        if (!timezoneEquals) {
            return false;
        }
        return true;
    }

    @Override
    public String string() {
        return string;
    }

    @Override
    public String date() {
        return date;
    }

    @Override
    public boolean recurring() {
        return recurring;
    }

    @Override
    public String datetime() {
        return datetime;
    }

    @Override
    public String timezone() {
        return timezone;
    }

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder {
        private String string;
        private String date;
        private boolean recurring;
        private String datetime;
        private String timezone;

        public Builder string(String string) {
            this.string = string;
            return this;
        }

        public Builder date(String date) {
            this.date = date;
            return this;
        }

        public Builder recurring(boolean recurring) {
            this.recurring = recurring;
            return this;
        }

        public Builder datetime(String datetime) {
            this.datetime = datetime;
            return this;
        }

        public Builder timezone(String timezone) {
            this.timezone = timezone;
            return this;
        }

        public ImmutableDue build() {
            return new ImmutableDue(this);
        }
    }
}