package com.rjesquivias.todoist;

import io.github.cdimascio.dotenv.Dotenv;
import java.net.http.HttpClient;
import java.util.Collection;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProjectDaoIntegrationTest {

  private static final Dotenv dotenv = Dotenv.load();
  private static final IProjectDao projectDao = new ProjectDao(
          HttpRequestHelper.build(HttpClient.newBuilder().build()),
          dotenv.get("PROJECT_URI"),
          dotenv.get("TODOIST_API_TOKEN"));

  private static final String projectName = "INT_TEST_PROJECT_NAME";

  @Test
  public void whenGetAll_returnsCreatedProject() {
    Project testProject = projectDao.create(Arguments.CreateProjectArgs.builder().name(projectName).build());
    Collection<Project> projects = projectDao.getAll();

    Project foundProject = projects.stream().filter(project -> project.equals(testProject))
        .findFirst().orElse(null);

    assertEquals(foundProject, testProject);
    projectDao.delete(testProject.id());
  }

  @Test
  public void whenGet_returnsCorrectProject() {
    Project testProject = projectDao.create(Arguments.CreateProjectArgs.builder().name(projectName).build());
    Project project = projectDao.get(testProject.id());
    assertEquals(project, testProject);
    projectDao.delete(testProject.id());
  }

  @Test
  public void whenCreate_objectIsCreatedSuccessfully() {
    Project createdProject = projectDao.create(Arguments.CreateProjectArgs.builder().name(projectName).build());
    Project queriedProject = projectDao.get(createdProject.id());

    assertEquals(createdProject, queriedProject);

    projectDao.delete(createdProject.id());
  }

  @Test
  public void whenUpdate_objectIsUpdatedSuccessfully() {
    final String newProjectName = "UPDATED_INT_TEST_PROJECT_NAME";
    Project createdProject = projectDao.create(Arguments.CreateProjectArgs.builder().name(projectName).build());
    projectDao.update(createdProject.id(),
            Arguments.UpdateProjectArgs.builder().name(newProjectName).color(Color.BLUE).favorite(true).build());
    Project queriedProject = projectDao.get(createdProject.id());

    assertNotEquals(createdProject, queriedProject);
    assertEquals(newProjectName, queriedProject.name());
    assertEquals(Color.BLUE, queriedProject.color());
    assertTrue(queriedProject.favorite());

    projectDao.delete(createdProject.id());
  }

  @Test
  public void whenDelete_objectIsDeletedSuccessfully() {
    Project createdProject = projectDao.create(Arguments.CreateProjectArgs.builder().name(projectName).build());
    Project queriedProject = projectDao.get(createdProject.id());
    assertEquals(createdProject, queriedProject);

    projectDao.delete(createdProject.id());
  }
}
