package com.rjesquivias.todoist.util.http;

import java.net.http.HttpResponse;

public interface ResponsePredicate {

  boolean isValid(HttpResponse response);
}
