package com.rjesquivias.todoist.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.rjesquivias.todoist.domain.Colors;
import com.rjesquivias.todoist.domain.Label;
import java.util.Collection;
import lombok.Builder;
import lombok.Data;

public interface ILabelDao extends IBaseDao {

  Collection<Label> getAll();

  Label create(CreateArgs args);

  @Data
  @Builder
  @JsonInclude(Include.NON_NULL)
  class CreateArgs {

    private String name = null;
    private Long order = null;
    private Colors color = null;
    private Boolean favorite = null;
  }

  Label get(long id);

  void update(long id, UpdateArgs args);

  @Data
  @Builder
  @JsonInclude(Include.NON_NULL)
  class UpdateArgs {

    private String name = null;
    private Long order = null;
    private Colors color = null;
    private Boolean favorite = null;
  }

  void delete(long id);
}
