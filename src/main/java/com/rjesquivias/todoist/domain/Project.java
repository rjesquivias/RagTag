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
  private long parentId;
  private long order;
  private long commentCount;
  private boolean shared;
  private boolean favorite;
  private boolean inboxProject;
  private boolean teamInbox;
  private long syncId;
  private String url;
}
