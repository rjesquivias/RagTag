package com.rjesquivias.todoist.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.stream.Stream;

public enum Colors {
  BERRY_RED(30),
  RED(31),
  ORANGE(32),
  YELLOW(33),
  OLIVE_GREEN(34),
  LIME_GREEN(35),
  GREEN(36),
  MINT_GREEN(37),
  TEAL(38),
  SKY_BLUE(39),
  LIGHT_BLUE(40),
  BLUE(41),
  GRAPE(42),
  VIOLET(43),
  LAVENDER(44),
  MAGENTA(45),
  SALMON(46),
  CHARCOAL(47),
  GREY(48),
  TAUPE(49);

  private long id;

  private Colors(long id) {
    this.id = id;
  }

  @JsonValue
  public long getId() {
    return id;
  }

  @JsonCreator
  static Colors fromId(int id) {
    return Stream.of(Colors.values()).filter(color -> color.id == id).findFirst().get();
  }
}
