package com.rjesquivias.todoist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import com.rjesquivias.todoist.domain.Label;
import io.github.cdimascio.dotenv.Dotenv;
import java.net.http.HttpClient;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

public class LabelDaoIntegrationTest {
  private static final Dotenv dotenv = Dotenv.load();
  private static final ILabelDao labelDao = new LabelDao(
      new HttpRequestHelper(HttpClient.newBuilder().build()),
      dotenv.get("LABEL_URI"),
      dotenv.get("TODOIST_API_TOKEN")
      );
  private static final Collection<String> labelNames = List.of("INT_TEST_LABEL_1",
      "INT_TEST_LABEL_2", "INT_TEST_LABEL_3", "INT_TEST_LABEL_4");
  private static final String labelNameOne = "INT_TEST_LABEL_1";
  private static final String labelNameTwo = "INT_TEST_LABEL_2";

  @Test
  public void whenGetAll_returnsAllLabels() {
    for (String labelName : labelNames) {
      labelDao.create(
          ILabelDao.CreateArgs.builder().name(labelName).build());
    }

    Collection<Label> labels = labelDao.getAll();

    for (String labelName : labelNames) {
      Label foundLabel = labels.stream()
          .filter(label -> label.name().equals(labelName))
          .findFirst().orElse(null);
      assertNotNull(labelName);

      labelDao.delete(foundLabel.id());
    }
  }

  @Test
  public void whenCreate_returnsCreatedLabel() {
    Label label = labelDao.create(ILabelDao.CreateArgs.builder().name(labelNameOne).build());

    Label queriedLabel = labelDao.get(label.id());

    assertEquals(label, queriedLabel);

    labelDao.delete(label.id());
  }

  @Test
  public void whenGet_returnsLabel() {
    Label label = labelDao.create(ILabelDao.CreateArgs.builder().name(labelNameOne).build());

    Label queriedLabel = labelDao.get(label.id());

    assertEquals(label, queriedLabel);

    labelDao.delete(label.id());
  }

  @Test
  public void whenUpdate_returnsUpdatedLabel() {
    Label label = labelDao.create(ILabelDao.CreateArgs.builder().name(labelNameOne).build());

    Label queriedLabel = labelDao.get(label.id());
    assertEquals(label, queriedLabel);

    labelDao.update(label.id(), ILabelDao.UpdateArgs.builder().name(labelNameTwo).build());
    queriedLabel = labelDao.get(label.id());

    assertNotEquals(label.name(), queriedLabel.name());
    assertEquals(labelNameTwo, queriedLabel.name());

    labelDao.delete(label.id());
  }

  @Test
  public void whenDelete_labelIsDeleted() {
    Label label = labelDao.create(ILabelDao.CreateArgs.builder().name(labelNameOne).build());
    labelDao.delete(label.id());
  }
}
