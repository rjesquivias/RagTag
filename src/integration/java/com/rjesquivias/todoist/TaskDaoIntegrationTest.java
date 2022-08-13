package com.rjesquivias.todoist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.rjesquivias.todoist.ITaskDao.GetAllActiveArgs;
import com.rjesquivias.todoist.domain.Task;
import io.github.cdimascio.dotenv.Dotenv;
import java.net.http.HttpClient;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

public class TaskDaoIntegrationTest {

  private static final Dotenv dotenv = Dotenv.load();
  private static final ITaskDao taskDao = new TaskDao(
          new HttpRequestHelper(HttpClient.newBuilder().build()),
          dotenv.get("TASK_URI"),
          dotenv.get("TODOIST_API_TOKEN"));

  private static final Collection<String> taskContent = List.of("INT_TEST_TASK_1",
      "INT_TEST_TASK_2", "INT_TEST_TASK_3", "INT_TEST_TASK_4");

  private static final String taskOne = "INT_TEST_TASK_1";

  private static final String taskTwo = "INT_TEST_TASK_2";


  @Test
  public void whenGetAllActive_returnsAllTasks() {
    for (String content : taskContent) {
      taskDao.create(ITaskDao.CreateArgs.builder().content(content).build());
    }

    Collection<Task> tasks = taskDao.getAllActive(GetAllActiveArgs.builder().build());

    for (String content : taskContent) {
      Task foundTask = tasks.stream().filter(task -> task.content().equals(content))
          .findFirst().orElse(null);
      assertNotNull(foundTask);
      taskDao.delete(foundTask.id());
    }
  }

  @Test
  public void whenCreate_taskIsCreated() {
    Task task = taskDao.create(ITaskDao.CreateArgs.builder().content(taskOne).build());
    Task queriedTask = taskDao.getActive(task.id());

    assertEquals(task, queriedTask);

    taskDao.delete(task.id());
  }

  @Test
  public void whenGetActive_correctTaskIsReturned() {
    Task task = taskDao.create(ITaskDao.CreateArgs.builder().content(taskOne).build());
    Task queriedTask = taskDao.getActive(task.id());

    assertEquals(task, queriedTask);

    taskDao.delete(task.id());
  }

  @Test
  public void whenUpdate_taskIsUpdated() {
    Task task = taskDao.create(ITaskDao.CreateArgs.builder().content(taskOne).build());
    taskDao.update(task.id(), ITaskDao.UpdateArgs.builder().content(taskTwo).build());
    Task queriedTask = taskDao.getActive(task.id());

    assertNotEquals(task.content(), queriedTask.content());
    assertEquals(taskTwo, queriedTask.content());

    taskDao.delete(task.id());
  }

  @Test
  public void whenClose_taskIsDeleted() {
    Task task = taskDao.create(ITaskDao.CreateArgs.builder().content(taskOne).build());
    assertFalse(task.completed());

    taskDao.close(task.id());
    Task queriedTask = taskDao.getActive(task.id());

    assertTrue(queriedTask.completed());

    taskDao.delete(task.id());
  }

  @Test
  public void whenReopen_taskIsDeleted() {
    Task task = taskDao.create(ITaskDao.CreateArgs.builder().content(taskOne).build());
    assertFalse(task.completed());

    taskDao.close(task.id());
    Task queriedTask = taskDao.getActive(task.id());

    assertTrue(queriedTask.completed());

    taskDao.reOpen(task.id());
    queriedTask = taskDao.getActive(task.id());

    assertFalse(queriedTask.completed());

    taskDao.delete(task.id());
  }

  @Test
  public void whenDelete_taskIsDeleted() {
    Task task = taskDao.create(ITaskDao.CreateArgs.builder().content(taskOne).build());
    taskDao.delete(task.id());
  }
}
