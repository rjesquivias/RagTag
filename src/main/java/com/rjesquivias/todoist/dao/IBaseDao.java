package com.rjesquivias.todoist.dao;

import com.rjesquivias.todoist.util.http.ResponsePredicate;
import org.apache.http.HttpStatus;

public interface IBaseDao {

  ResponsePredicate okPredicate = response ->
      response.statusCode() == HttpStatus.SC_OK;
  ResponsePredicate noContentPredicate = response ->
      response.statusCode() == HttpStatus.SC_NO_CONTENT;
}
