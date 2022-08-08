package com.rjesquivias.todoist.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.rjesquivias.todoist.dao.IProjectDao.CreateArgs;
import com.rjesquivias.todoist.dao.IProjectDao.UpdateArgs;
import com.rjesquivias.todoist.domain.Colors;
import com.rjesquivias.todoist.domain.Project;
import io.github.cdimascio.dotenv.Dotenv;
import java.net.http.HttpClient;
import java.util.Collection;
import org.junit.Test;

public class ProjectDaoIntegrationTest {

  private static final IProjectDao projectDao = new ProjectDao(HttpClient.newBuilder().build(),
      Dotenv.load());

  private static final String projectName = "INT_TEST_PROJECT_NAME";

  @Test
  public void whenGetAll_returnsCreatedProject() {
    Project testProject = projectDao.create(CreateArgs.builder().name(projectName).build());
    Collection<Project> projects = projectDao.getAll();

    Project foundProject = projects.stream().filter(project -> project.equals(testProject))
        .findFirst().orElse(null);

    assertEquals(foundProject, testProject);
    projectDao.delete(testProject.getId());
  }

  @Test
  public void whenGet_returnsCorrectProject() {
    Project testProject = projectDao.create(CreateArgs.builder().name(projectName).build());
    Project project = projectDao.get(testProject.getId());
    assertEquals(project, testProject);
    projectDao.delete(testProject.getId());
  }

  @Test
  public void whenCreate_objectIsCreatedSuccessfully() {
    Project createdProject = projectDao.create(CreateArgs.builder().name(projectName).build());
    Project queriedProject = projectDao.get(createdProject.getId());

    assertEquals(createdProject, queriedProject);

    projectDao.delete(createdProject.getId());
  }

  @Test
  public void whenUpdate_objectIsUpdatedSuccessfully() {
    final String newProjectName = "UPDATED_INT_TEST_PROJECT_NAME";
    Project createdProject = projectDao.create(CreateArgs.builder().name(projectName).build());
    projectDao.update(createdProject.getId(),
        UpdateArgs.builder().name(newProjectName).color(Colors.BLUE).favorite(true).build());
    Project queriedProject = projectDao.get(createdProject.getId());

    assertNotEquals(createdProject, queriedProject);
    createdProject.setName(newProjectName);
    createdProject.setColor(Colors.BLUE);
    createdProject.setFavorite(true);
    assertEquals(createdProject, queriedProject);

    projectDao.delete(createdProject.getId());
  }

  @Test
  public void whenDelete_objectIsDeletedSuccessfully() throws InterruptedException {
    Project createdProject = projectDao.create(CreateArgs.builder().name(projectName).build());
    Project queriedProject = projectDao.get(createdProject.getId());
    assertEquals(createdProject, queriedProject);

    projectDao.delete(createdProject.getId());
  }
}
