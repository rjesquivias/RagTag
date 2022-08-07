package com.rjesquivias.todoist.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

  private long id;
  private long projectId;
  private long sectionId;
  private String content;
  private String description;
  private boolean completed;
  private Collection<Integer> labelIds;
  private long parentId;
  private long order;
  private long priority;
  private Due due;
  private String url;
  private long commentCount;
  private long assignee;
  private long assigner;

  @Data
  private class Due {

    private String string;
    private String date;
    private boolean recurring;
    private String datetime;
    private String timezone;
  }
}
