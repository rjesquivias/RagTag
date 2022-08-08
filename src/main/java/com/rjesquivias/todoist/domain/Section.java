package com.rjesquivias.todoist.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Section {

  private long id;
  private long project_id;
  private long order;
  private String name;
}
