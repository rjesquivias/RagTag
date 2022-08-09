package com.rjesquivias.todoist;

import java.net.http.HttpResponse;

interface ResponsePredicate {

    boolean isValid(HttpResponse<?> response);
}
