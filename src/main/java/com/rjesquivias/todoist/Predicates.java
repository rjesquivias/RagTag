package com.rjesquivias.todoist;

import org.apache.http.HttpStatus;

final class Predicates {
    private Predicates() {}

    static final ResponsePredicate okPredicate = response ->
            response.statusCode() == HttpStatus.SC_OK;
    static final ResponsePredicate noContentPredicate = response ->
            response.statusCode() == HttpStatus.SC_NO_CONTENT;
}
