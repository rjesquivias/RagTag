package com.rjesquivias.todoist.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import com.google.common.collect.ImmutableList;
import com.rjesquivias.todoist.dao.ICommentDao.CreateArgs;
import com.rjesquivias.todoist.domain.Comment;
import com.rjesquivias.todoist.domain.Project;
import com.rjesquivias.todoist.domain.Task;
import com.rjesquivias.todoist.util.http.HttpRequestHelper;
import io.github.cdimascio.dotenv.Dotenv;
import java.net.http.HttpClient;
import java.util.Collection;
import org.junit.Test;

public class CommentDaoIntegrationTest {

  private static final IProjectDao projectDao = new ProjectDao(HttpClient.newBuilder().build(),
      Dotenv.load());
  private static final ITaskDao taskDao = new TaskDao(
      new HttpRequestHelper(HttpClient.newBuilder().build()),
      Dotenv.load());
  private static final ICommentDao commentDao = new CommentDao(
      new HttpRequestHelper(HttpClient.newBuilder().build()),
      Dotenv.load());

  private static final String testprojectName = "INT_TEST_PROJECT_NAME_1";

  private static final Collection<String> testContent = ImmutableList.of("INT_TEST_CONTENT_1",
      "INT_TEST_CONTENT_2", "INT_TEST_CONTENT_3", "INT_TEST_CONTENT_4");

  private static final String testContentOne = "INT_TEST_CONTENT_1";
  private static final String testContentTwo = "INT_TEST_CONTENT_2";
  private static final String testCommentContentOne = "INT_TEST_CONTENT_1";
  private static final String testCommentContentTwo = "INT_TEST_CONTENT_2";


  @Test
  public void whenGetAllInProject_returnsAllComments() {
    Project project = projectDao.create(
        IProjectDao.CreateArgs.builder().name(testprojectName).build());

    for (String content : testContent) {
      commentDao.create(
          CreateArgs.builder().project_id(project.getId()).content(content).build());
    }

    Collection<Comment> comments = commentDao.getAllInProject(project.getId());

    for (String content : testContent) {
      Comment foundComment = comments.stream()
          .filter(comment -> comment.getContent().equals(content))
          .findFirst().orElse(null);
      assertNotNull(foundComment);

      commentDao.delete(foundComment.getId());
    }

    projectDao.delete(project.getId());
  }

  @Test
  public void whenGetAllInTask_returnsAllComments() {
    Task task = taskDao.create(ITaskDao.CreateArgs.builder().content(testContentOne).build());

    for (String content : testContent) {
      commentDao.create(
          CreateArgs.builder().task_id(task.getId()).content(content).build());
    }

    Collection<Comment> comments = commentDao.getAllInTask(task.getId());

    for (String content : testContent) {
      Comment foundComment = comments.stream()
          .filter(comment -> comment.getContent().equals(content))
          .findFirst().orElse(null);
      assertNotNull(foundComment);

      commentDao.delete(foundComment.getId());
    }

    taskDao.delete(task.getId());
  }

  @Test
  public void whenCreate_returnsCreatedComment() {
    Task task = taskDao.create(ITaskDao.CreateArgs.builder().content(testContentOne).build());
    Comment comment = commentDao.create(
        CreateArgs.builder().task_id(task.getId()).content(testCommentContentOne).build());

    Comment queriedComment = commentDao.get(comment.getId());

    assertEquals(comment, queriedComment);

    commentDao.delete(comment.getId());
    taskDao.delete(task.getId());
  }

  @Test
  public void whenGet_returnsComment() {
    Task task = taskDao.create(ITaskDao.CreateArgs.builder().content(testContentOne).build());
    Comment comment = commentDao.create(
        CreateArgs.builder().task_id(task.getId()).content(testCommentContentOne).build());

    Comment queriedComment = commentDao.get(comment.getId());

    assertEquals(comment, queriedComment);

    commentDao.delete(comment.getId());
    taskDao.delete(task.getId());
  }

  @Test
  public void whenUpdate_returnsUpdatedComment() {
    Task task = taskDao.create(ITaskDao.CreateArgs.builder().content(testContentOne).build());
    Comment comment = commentDao.create(
        CreateArgs.builder().task_id(task.getId()).content(testCommentContentOne).build());

    Comment queriedComment = commentDao.get(comment.getId());
    assertEquals(comment, queriedComment);

    commentDao.update(comment.getId(), testCommentContentTwo);
    queriedComment = commentDao.get(comment.getId());

    assertNotEquals(comment.getContent(), queriedComment.getContent());
    assertEquals(testCommentContentTwo, queriedComment.getContent());

    commentDao.delete(comment.getId());
    taskDao.delete(task.getId());
  }

  @Test
  public void whenDelete_commentIsDeleted() {
    Task task = taskDao.create(ITaskDao.CreateArgs.builder().content(testContentOne).build());
    Comment comment = commentDao.create(
        CreateArgs.builder().task_id(task.getId()).content(testCommentContentOne).build());
    commentDao.delete(comment.getId());
    taskDao.delete(task.getId());
  }
}
