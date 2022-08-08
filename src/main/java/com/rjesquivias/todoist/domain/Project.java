package com.rjesquivias.todoist.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project {

  private long id;
  private String name;
  private Colors color;
  private long parent_id;
  private long order;
  private long comment_count;
  private boolean shared;
  private boolean favorite;
  private boolean inbox_project;
  private boolean team_inbox;
  private long sync_id;
  private String url;
}
