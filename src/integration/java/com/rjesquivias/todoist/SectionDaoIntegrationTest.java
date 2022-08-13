package com.rjesquivias.todoist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import com.rjesquivias.todoist.IProjectDao.CreateArgs;
import com.rjesquivias.todoist.domain.Project;
import com.rjesquivias.todoist.domain.Section;
import io.github.cdimascio.dotenv.Dotenv;
import java.net.http.HttpClient;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

public class SectionDaoIntegrationTest {

  private static final Dotenv dotenv = Dotenv.load();
  private static final ISectionDao sectionDao = new SectionDao(
          new HttpRequestHelper(HttpClient.newBuilder().build()),
          dotenv.get("SECTION_URI"),
          dotenv.get("TODOIST_API_TOKEN"));

  private static final IProjectDao projectDao = new ProjectDao(
          HttpClient.newBuilder().build(),
          dotenv.get("PROJECT_URI"),
          dotenv.get("TODOIST_API_TOKEN"));

  private static final String projectName = "INT_TEST_PROJECT_NAME";
  private static final Collection<String> sectionNames = List.of("INT_TEST_SECTION_1",
      "INT_TEST_SECTION_2", "INT_TEST_SECTION_3", "INT_TEST_SECTION_4");

  private static final String sectionOne = "INT_TEST_SECTION_1";

  private static final String sectionTwo = "INT_TEST_SECTION_2";


  @Test
  public void whenGetAll_returnsAllSections() {
    Project testProject = projectDao.create(CreateArgs.builder().name(projectName).build());
    for (String name : sectionNames) {
      sectionDao.create(
          ISectionDao.CreateArgs.builder().name(name).project_id(testProject.id()).build());
    }

    Collection<Section> sections = sectionDao.getAll();

    for (String name : sectionNames) {
      Section foundSection = sections.stream().filter(section -> section.name().equals(name))
          .findFirst().orElse(null);
      assertNotNull(foundSection);

      projectDao.delete(testProject.id());
    }
  }

  @Test
  public void whenGetAll_filterByProjectId_returnsOnlySectionsInProject() {
    Project testProject = projectDao.create(CreateArgs.builder().name(projectName).build());
    for (String name : sectionNames) {
      sectionDao.create(
          ISectionDao.CreateArgs.builder().name(name).project_id(testProject.id()).build());
    }

    Collection<Section> sections = sectionDao.getAll(testProject.id());

    assertEquals(sectionNames.size(), sections.size());
    for (String name : sectionNames) {
      Section foundSection = sections.stream().filter(section -> section.name().equals(name))
          .findFirst().orElse(null);
      assertNotNull(foundSection);

      projectDao.delete(testProject.id());
    }
  }

  @Test
  public void whenCreate_sectionIsCreated() {
    Project testProject = projectDao.create(CreateArgs.builder().name(projectName).build());
    sectionDao.create(ISectionDao.CreateArgs.builder().name(sectionOne).project_id(
        testProject.id()).build());

    Collection<Section> sections = sectionDao.getAll(testProject.id());

    assertEquals(1, sections.size());
    Section section = sections.iterator().next();
    assertEquals(sectionOne, section.name());
    assertEquals(testProject.id(), section.projectId());

    projectDao.delete(testProject.id());
  }

  @Test
  public void whenGet_correctSectionIsReturned() {
    Project testProject = projectDao.create(CreateArgs.builder().name(projectName).build());
    Section s1 = sectionDao.create(ISectionDao.CreateArgs.builder().name(sectionOne).project_id(
        testProject.id()).build());
    Section s2 = sectionDao.create(ISectionDao.CreateArgs.builder().name(sectionTwo).project_id(
        testProject.id()).build());

    Section queriedS1 = sectionDao.get(s1.id());
    Section queriedS2 = sectionDao.get(s2.id());

    assertEquals(s1, queriedS1);
    assertEquals(s2, queriedS2);

    projectDao.delete(testProject.id());
  }

  @Test
  public void whenUpdate_sectionIsUpdated() {
    Project testProject = projectDao.create(CreateArgs.builder().name(projectName).build());
    Section s1 = sectionDao.create(ISectionDao.CreateArgs.builder().name(sectionOne).project_id(
        testProject.id()).build());
    sectionDao.update(s1.id(), sectionTwo);
    Section s2 = sectionDao.get(s1.id());

    assertNotEquals(s1.name(), s2.name());
    assertEquals(sectionTwo, s2.name());

    projectDao.delete(testProject.id());
  }

  @Test
  public void whenDelete_sectionIsDeleted() {
    Project testProject = projectDao.create(CreateArgs.builder().name(projectName).build());
    Section s1 = sectionDao.create(ISectionDao.CreateArgs.builder().name(sectionOne).project_id(
        testProject.id()).build());

    sectionDao.delete(s1.id());
  }
}
