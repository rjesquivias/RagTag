package com.rjesquivias.todoist.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.rjesquivias.todoist.domain.Section;
import java.util.Collection;
import lombok.Builder;
import lombok.Data;

public interface ISectionDao {

  Collection<Section> getAll();

  Collection<Section> getAll(long project_id);

  Section create(CreateArgs args);

  Section get(long id);

  void update(long id, String name);

  void delete(long id);

  @Data
  @Builder
  @JsonInclude(Include.NON_NULL)
  class CreateArgs {

    private String name = null;
    private Long project_id = null;
    private Long order = null;
  }
}
