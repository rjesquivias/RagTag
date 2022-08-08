package com.rjesquivias.todoist.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.rjesquivias.todoist.domain.Task;
import java.util.Collection;
import lombok.Builder;
import lombok.Data;

public interface ITaskDao extends IBaseDao {

  Collection<Task> getAllActive(GetAllActiveArgs args);

  @Data
  @Builder
  @JsonInclude(Include.NON_NULL)
  class GetAllActiveArgs {

    private Long project_id = null;
    private Long section_id = null;
    private Long label_id = null;
    private String filter = null;
    private String lang = null;
    private Collection<Long> ids = null;
  }

  Task create(CreateArgs args);

  @Data
  @Builder
  @JsonInclude(Include.NON_NULL)
  class CreateArgs {

    private String content;
    private String description = null;
    private Long project_id = null;
    private Long section_id = null;
    private Long parent_id = null;
    private Long parent = null;
    private Long order = null;
    private Collection<Long> label_ids = null;
    private Long priority = null;
    private String due_string = null;
    private String due_date = null;
    private String due_datetime = null;
    private String due_lang = null;
    private Long assignee = null;
  }

  Task getActive(long id);

  void update(long id, UpdateArgs args);

  @Data
  @Builder
  @JsonInclude(Include.NON_NULL)
  class UpdateArgs {

    private String content = null;
    private String description = null;
    private Collection<Long> label_ids = null;
    private Long priority = null;
    private String due_string = null;
    private String due_date = null;
    private String due_datetime = null;
    private String due_lang = null;
    private Long assignee = null;
  }

  void close(long id);

  void reOpen(long id);

  void delete(long id);
}
