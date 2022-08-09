package com.rjesquivias.todoist;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.rjesquivias.todoist.domain.Comment;
import java.util.Collection;
import lombok.Builder;
import lombok.Data;

interface ICommentDao {

  Collection<Comment> getAllInProject(long projectId);

  Collection<Comment> getAllInTask(long taskId);

  Comment create(CreateArgs args);

  @Data
  @Builder
  @JsonInclude(Include.NON_NULL)
  class CreateArgs {

    private Long task_id = null;
    private Long project_id = null;
    private String content = null;
  }

  Comment get(long commentId);

  void update(long commentId, String content);

  void delete(long commentId);
}
