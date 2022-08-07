package com.rjesquivias.todoist.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Label {

  private long id;
  private String name;
  private long color;
  private long order;
  private boolean favorite;
}
