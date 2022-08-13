package com.rjesquivias.todoist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import io.github.cdimascio.dotenv.Dotenv;
import java.net.http.HttpClient;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

public class CommentDaoIntegrationTest {
  private static final Dotenv dotenv = Dotenv.load();
  private static final IProjectDao projectDao = new ProjectDao(
          HttpRequestHelper.build(HttpClient.newBuilder().build()),
          dotenv.get("PROJECT_URI"),
          dotenv.get("TODOIST_API_TOKEN"));
  private static final ITaskDao taskDao = new TaskDao(
          HttpRequestHelper.build(HttpClient.newBuilder().build()),
          dotenv.get("TASK_URI"),
          dotenv.get("TODOIST_API_TOKEN"));
  private static final ICommentDao commentDao = new CommentDao(
          HttpRequestHelper.build(HttpClient.newBuilder().build()),
          dotenv.get("COMMENT_URI"),
          dotenv.get("TODOIST_API_TOKEN"));
  private static final String testprojectName = "INT_TEST_PROJECT_NAME_1";
  private static final Collection<String> testContent = List.of("INT_TEST_CONTENT_1",
      "INT_TEST_CONTENT_2", "INT_TEST_CONTENT_3", "INT_TEST_CONTENT_4");
  private static final String testContentOne = "INT_TEST_CONTENT_1";
  private static final String testCommentContentOne = "INT_TEST_CONTENT_1";
  private static final String testCommentContentTwo = "INT_TEST_CONTENT_2";


  @Test
  public void whenGetAllInProject_returnsAllComments() {
    Project project = projectDao.create(
        Arguments.CreateProjectArgs.builder().name(testprojectName).build());

    for (String content : testContent) {
      commentDao.create(
          Arguments.CreateCommentArgs.builder().project_id(project.id()).content(content).build());
    }

    Collection<Comment> comments = commentDao.getAllInProject(project.id());

    for (String content : testContent) {
      Comment foundComment = comments.stream()
          .filter(comment -> comment.content().equals(content))
          .findFirst().orElse(null);
      assertNotNull(foundComment);

      commentDao.delete(foundComment.id());
    }

    projectDao.delete(project.id());
  }

  @Test
  public void whenGetAllInTask_returnsAllComments() {
    Task task = taskDao.create(Arguments.CreateTaskArgs.builder().content(testContentOne).build());

    for (String content : testContent) {
      commentDao.create(
          Arguments.CreateCommentArgs.builder().task_id(task.id()).content(content).build());
    }

    Collection<Comment> comments = commentDao.getAllInTask(task.id());

    for (String content : testContent) {
      Comment foundComment = comments.stream()
          .filter(comment -> comment.content().equals(content))
          .findFirst().orElse(null);
      assertNotNull(foundComment);

      commentDao.delete(foundComment.id());
    }

    taskDao.delete(task.id());
  }

  @Test
  public void whenCreate_returnsCreatedComment() {
    Task task = taskDao.create(Arguments.CreateTaskArgs.builder().content(testContentOne).build());
    Comment comment = commentDao.create(
        Arguments.CreateCommentArgs.builder().task_id(task.id()).content(testCommentContentOne).build());

    Comment queriedComment = commentDao.get(comment.id());

    assertEquals(comment, queriedComment);

    commentDao.delete(comment.id());
    taskDao.delete(task.id());
  }

  @Test
  public void whenGet_returnsComment() {
    Task task = taskDao.create(Arguments.CreateTaskArgs.builder().content(testContentOne).build());
    Comment comment = commentDao.create(
        Arguments.CreateCommentArgs.builder().task_id(task.id()).content(testCommentContentOne).build());

    Comment queriedComment = commentDao.get(comment.id());

    assertEquals(comment, queriedComment);

    commentDao.delete(comment.id());
    taskDao.delete(task.id());
  }

  @Test
  public void whenUpdate_returnsUpdatedComment() {
    Task task = taskDao.create(Arguments.CreateTaskArgs.builder().content(testContentOne).build());
    Comment comment = commentDao.create(
        Arguments.CreateCommentArgs.builder().task_id(task.id()).content(testCommentContentOne).build());

    Comment queriedComment = commentDao.get(comment.id());
    assertEquals(comment, queriedComment);

    commentDao.update(comment.id(), testCommentContentTwo);
    queriedComment = commentDao.get(comment.id());

    assertNotEquals(comment.content(), queriedComment.content());
    assertEquals(testCommentContentTwo, queriedComment.content());

    commentDao.delete(comment.id());
    taskDao.delete(task.id());
  }

  @Test
  public void whenDelete_commentIsDeleted() {
    Task task = taskDao.create(Arguments.CreateTaskArgs.builder().content(testContentOne).build());
    Comment comment = commentDao.create(
        Arguments.CreateCommentArgs.builder().task_id(task.id()).content(testCommentContentOne).build());
    commentDao.delete(comment.id());
    taskDao.delete(task.id());
  }
}
