package com.rjesquivias.todoist.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.ImmutableList;
import com.rjesquivias.todoist.dao.ITaskDao.GetAllActiveArgs;
import com.rjesquivias.todoist.domain.Task;
import com.rjesquivias.todoist.util.http.HttpRequestHelper;
import io.github.cdimascio.dotenv.Dotenv;
import java.net.http.HttpClient;
import java.util.Collection;
import org.junit.Test;

public class TaskDaoIntegrationTest {

  private static final ITaskDao taskDao = new TaskDao(
      new HttpRequestHelper(HttpClient.newBuilder().build()),
      Dotenv.load());

  private static final Collection<String> taskContent = ImmutableList.of("INT_TEST_TASK_1",
      "INT_TEST_TASK_2", "INT_TEST_TASK_3", "INT_TEST_TASK_4");

  private static final String taskOne = "INT_TEST_TASK_1";

  private static final String taskTwo = "INT_TEST_TASK_2";


  @Test
  public void whenGetAllActive_returnsAllTasks() {
    for (String name : taskContent) {
      taskDao.create(ITaskDao.CreateArgs.builder().content(name).build());
    }

    Collection<Task> tasks = taskDao.getAllActive(GetAllActiveArgs.builder().build());

    for (String name : taskContent) {
      Task foundTask = tasks.stream().filter(task -> task.getContent().equals(name))
          .findFirst().orElse(null);
      assertNotNull(foundTask);
      taskDao.delete(foundTask.getId());
    }
  }

  @Test
  public void whenCreate_taskIsCreated() {
    Task task = taskDao.create(ITaskDao.CreateArgs.builder().content(taskOne).build());
    Task queriedTask = taskDao.getActive(task.getId());

    assertEquals(task, queriedTask);

    taskDao.delete(task.getId());
  }

  @Test
  public void whenGetActive_correctTaskIsReturned() {
    Task task = taskDao.create(ITaskDao.CreateArgs.builder().content(taskOne).build());
    Task queriedTask = taskDao.getActive(task.getId());

    assertEquals(task, queriedTask);

    taskDao.delete(task.getId());
  }

  @Test
  public void whenUpdate_taskIsUpdated() {
    Task task = taskDao.create(ITaskDao.CreateArgs.builder().content(taskOne).build());
    taskDao.update(task.getId(), ITaskDao.UpdateArgs.builder().content(taskTwo).build());
    Task queriedTask = taskDao.getActive(task.getId());

    assertNotEquals(task.getContent(), queriedTask.getContent());
    assertEquals(taskTwo, queriedTask.getContent());

    taskDao.delete(task.getId());
  }

  @Test
  public void whenClose_taskIsDeleted() {
    Task task = taskDao.create(ITaskDao.CreateArgs.builder().content(taskOne).build());
    assertFalse(task.isCompleted());

    taskDao.close(task.getId());
    Task queriedTask = taskDao.getActive(task.getId());

    assertTrue(queriedTask.isCompleted());

    taskDao.delete(task.getId());
  }

  @Test
  public void whenReopen_taskIsDeleted() {
    Task task = taskDao.create(ITaskDao.CreateArgs.builder().content(taskOne).build());
    assertFalse(task.isCompleted());

    taskDao.close(task.getId());
    Task queriedTask = taskDao.getActive(task.getId());

    assertTrue(queriedTask.isCompleted());

    taskDao.reOpen(task.getId());
    queriedTask = taskDao.getActive(task.getId());

    assertFalse(queriedTask.isCompleted());

    taskDao.delete(task.getId());
  }

  @Test
  public void whenDelete_taskIsDeleted() {
    Task task = taskDao.create(ITaskDao.CreateArgs.builder().content(taskOne).build());
    taskDao.delete(task.getId());
  }
}
