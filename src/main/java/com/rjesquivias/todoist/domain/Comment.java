package com.rjesquivias.todoist.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

  private long id;
  private long taskId;
  private long projectId;
  private String posted;
  private String content;
}
