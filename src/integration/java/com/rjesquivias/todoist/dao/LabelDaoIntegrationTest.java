package com.rjesquivias.todoist.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import com.google.common.collect.ImmutableList;
import com.rjesquivias.todoist.dao.ILabelDao.CreateArgs;
import com.rjesquivias.todoist.dao.ILabelDao.UpdateArgs;
import com.rjesquivias.todoist.domain.Label;
import com.rjesquivias.todoist.util.http.HttpRequestHelper;
import io.github.cdimascio.dotenv.Dotenv;
import java.net.http.HttpClient;
import java.util.Collection;
import org.junit.Test;

public class LabelDaoIntegrationTest {

  private static final ILabelDao labelDao = new LabelDao(
      new HttpRequestHelper(HttpClient.newBuilder().build()),
      Dotenv.load());
  private static final Collection<String> labelNames = ImmutableList.of("INT_TEST_LABEL_1",
      "INT_TEST_LABEL_2", "INT_TEST_LABEL_3", "INT_TEST_LABEL_4");
  private static final String labelNameOne = "INT_TEST_LABEL_1";
  private static final String labelNameTwo = "INT_TEST_LABEL_2";

  @Test
  public void whenGetAll_returnsAllLabels() {
    for (String labelName : labelNames) {
      labelDao.create(
          CreateArgs.builder().name(labelName).build());
    }

    Collection<Label> labels = labelDao.getAll();

    for (String labelName : labelNames) {
      Label foundLabel = labels.stream()
          .filter(label -> label.getName().equals(labelName))
          .findFirst().orElse(null);
      assertNotNull(labelName);

      labelDao.delete(foundLabel.getId());
    }
  }

  @Test
  public void whenCreate_returnsCreatedLabel() {
    Label label = labelDao.create(CreateArgs.builder().name(labelNameOne).build());

    Label queriedLabel = labelDao.get(label.getId());

    assertEquals(label, queriedLabel);

    labelDao.delete(label.getId());
  }

  @Test
  public void whenGet_returnsLabel() {
    Label label = labelDao.create(CreateArgs.builder().name(labelNameOne).build());

    Label queriedLabel = labelDao.get(label.getId());

    assertEquals(label, queriedLabel);

    labelDao.delete(label.getId());
  }

  @Test
  public void whenUpdate_returnsUpdatedLabel() {
    Label label = labelDao.create(CreateArgs.builder().name(labelNameOne).build());

    Label queriedLabel = labelDao.get(label.getId());
    assertEquals(label, queriedLabel);

    labelDao.update(label.getId(), UpdateArgs.builder().name(labelNameTwo).build());
    queriedLabel = labelDao.get(label.getId());

    assertNotEquals(label.getName(), queriedLabel.getName());
    assertEquals(labelNameTwo, queriedLabel.getName());

    labelDao.delete(label.getId());
  }

  @Test
  public void whenDelete_labelIsDeleted() {
    Label label = labelDao.create(CreateArgs.builder().name(labelNameOne).build());
    labelDao.delete(label.getId());
  }
}
