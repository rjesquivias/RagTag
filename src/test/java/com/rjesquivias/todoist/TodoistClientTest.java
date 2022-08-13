package com.rjesquivias.todoist;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static com.rjesquivias.todoist.TestConstants.*;
import org.junit.Test;
import org.mockito.Mockito;

public class TodoistClientTest {

  private final TodoistClient client = buildMockClient();
  private static final IProjectDao mockedProjectDao = Mockito.mock(IProjectDao.class);
  private static final ISectionDao mockedSectionDao = Mockito.mock(ISectionDao.class);
  private static final ITaskDao mockedTaskDao = Mockito.mock(ITaskDao.class);
  private static final ICommentDao mockedCommentDao = Mockito.mock(ICommentDao.class);
  private static final ILabelDao mockedLabelDao = Mockito.mock(ILabelDao.class);

  @Test
  public void getProjects() {
    client.getProjects();
    verify(mockedProjectDao, times(1)).getAll();
  }

  @Test
  public void getProject() {
    client.getProject(testProjectId);
    verify(mockedProjectDao, times(1)).get(testProjectId);
  }

  @Test
  public void createProject() {
    client.createProject(createProjectArgs);
    verify(mockedProjectDao, times(1)).create(createProjectArgs);
  }

  @Test
  public void updateProject() {
    client.updateProject(testProjectId, updateProjectArgs);
    verify(mockedProjectDao, times(1)).update(testProjectId, updateProjectArgs);
  }

  @Test
  public void deleteProject() {
    client.deleteProject(testProjectId);
    verify(mockedProjectDao, times(1)).delete(testProjectId);
  }

  @Test
  public void getSections() {
    client.getSections();
    verify(mockedSectionDao, times(1)).getAll();
  }

  @Test
  public void getSectionsOfProject() {
    client.getSectionsOfProject(testProjectId);
    verify(mockedSectionDao, times(1)).getAll(testProjectId);
  }

  @Test
  public void createSection() {
    client.createSection(createSectionArgs);
    verify(mockedSectionDao, times(1)).create(createSectionArgs);
  }

  @Test
  public void getSection() {
    client.getSection(testSectionId);
    verify(mockedSectionDao, times(1)).get(testSectionId);
  }

  @Test
  public void updateSection() {
    client.updateSection(testSectionId, testName);
    verify(mockedSectionDao, times(1)).update(testSectionId, testName);
  }

  @Test
  public void deleteSection() {
    client.deleteSection(testSectionId);
    verify(mockedSectionDao, times(1)).delete(testSectionId);
  }

  @Test
  public void getTasks() {
    client.getTasks(getAllActiveTaskArgs);
    verify(mockedTaskDao, times(1)).getAllActive(getAllActiveTaskArgs);
  }

  @Test
  public void createTask() {
    client.createTask(createTaskArgs);
    verify(mockedTaskDao, times(1)).create(createTaskArgs);
  }

  @Test
  public void getTask() {
    client.getTask(testTaskId);
    verify(mockedTaskDao, times(1)).getActive(testTaskId);
  }

  @Test
  public void updateTask() {
    client.updateTask(testTaskId, updateTaskArgs);
    verify(mockedTaskDao, times(1)).update(testTaskId, updateTaskArgs);
  }

  @Test
  public void closeTask() {
    client.closeTask(testTaskId);
    verify(mockedTaskDao, times(1)).close(testTaskId);
  }

  @Test
  public void reOpenTask() {
    client.reOpenTask(testTaskId);
    verify(mockedTaskDao, times(1)).reOpen(testTaskId);
  }

  @Test
  public void deleteTask() {
    client.deleteTask(testTaskId);
    verify(mockedTaskDao, times(1)).delete(testTaskId);
  }

  @Test
  public void getCommentsOfProject() {
    client.getCommentsOfProject(testProjectId);
    verify(mockedCommentDao, times(1)).getAllInProject(testProjectId);
  }

  @Test
  public void getCommentsOfTask() {
    client.getCommentsOfTask(testTaskId);
    verify(mockedCommentDao, times(1)).getAllInTask(testTaskId);
  }

  @Test
  public void createComment() {
    client.createComment(createCommentArgs);
    verify(mockedCommentDao, times(1)).create(createCommentArgs);
  }

  @Test
  public void getComment() {
    client.getComment(testCommentId);
    verify(mockedCommentDao, times(1)).get(testCommentId);
  }

  @Test
  public void updateComment() {
    client.updateComment(testCommentId, testContent);
    verify(mockedCommentDao, times(1)).update(testCommentId, testContent);
  }

  @Test
  public void deleteComment() {
    client.deleteComment(testCommentId);
    verify(mockedCommentDao, times(1)).delete(testCommentId);
  }

  @Test
  public void getLabels() {
    client.getLabels();
    verify(mockedLabelDao, times(1)).getAll();
  }

  @Test
  public void createLabel() {
    client.createLabel(createLabelArgs);
    verify(mockedLabelDao, times(1)).create(createLabelArgs);
  }

  @Test
  public void getLabel() {
    client.getLabel(testLabelId);
    verify(mockedLabelDao, times(1)).get(testLabelId);
  }

  @Test
  public void updateLabel() {
    client.updateLabel(testLabelId, updateLabelArgs);
    verify(mockedLabelDao, times(1)).update(testLabelId, updateLabelArgs);
  }

  @Test
  public void deleteLabel() {
    client.deleteLabel(testLabelId);
    verify(mockedLabelDao, times(1)).delete(testLabelId);
  }

  private static TodoistClient buildMockClient() {
    return TodoistClient.builder()
        .projectDao(mockedProjectDao)
        .sectionDao(mockedSectionDao)
        .taskDao(mockedTaskDao)
        .commentDao(mockedCommentDao)
        .labelDao(mockedLabelDao).build();
  }
}
