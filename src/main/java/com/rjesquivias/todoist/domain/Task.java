package com.rjesquivias.todoist.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {

  private long id;
  private long project_id;
  private long section_id;
  private String content;
  private String description;
  private boolean completed;
  private Collection<Integer> label_ids;
  private long parent_id;
  private long order;
  private long priority;
  private Due due;
  private String url;
  private long comment_count;
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
