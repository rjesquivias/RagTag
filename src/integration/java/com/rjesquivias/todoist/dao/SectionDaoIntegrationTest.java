package com.rjesquivias.todoist.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import com.google.common.collect.ImmutableList;
import com.rjesquivias.todoist.dao.IProjectDao.CreateArgs;
import com.rjesquivias.todoist.domain.Project;
import com.rjesquivias.todoist.domain.Section;
import com.rjesquivias.todoist.util.http.HttpRequestHelper;
import io.github.cdimascio.dotenv.Dotenv;
import java.net.http.HttpClient;
import java.util.Collection;
import org.junit.Test;

public class SectionDaoIntegrationTest {

  private static final ISectionDao sectionDao = new SectionDao(
      new HttpRequestHelper(HttpClient.newBuilder().build()),
      Dotenv.load());

  private static final IProjectDao projectDao = new ProjectDao(HttpClient.newBuilder().build(),
      Dotenv.load());

  private static final String projectName = "INT_TEST_PROJECT_NAME";
  private static final Collection<String> sectionNames = ImmutableList.of("INT_TEST_SECTION_1",
      "INT_TEST_SECTION_2", "INT_TEST_SECTION_3", "INT_TEST_SECTION_4");

  private static final String sectionOne = "INT_TEST_SECTION_1";

  private static final String sectionTwo = "INT_TEST_SECTION_2";


  @Test
  public void whenGetAll_returnsAllSections() {
    Project testProject = projectDao.create(CreateArgs.builder().name(projectName).build());
    for (String name : sectionNames) {
      sectionDao.create(
          ISectionDao.CreateArgs.builder().name(name).project_id(testProject.getId()).build());
    }

    Collection<Section> sections = sectionDao.getAll();

    for (String name : sectionNames) {
      Section foundSection = sections.stream().filter(section -> section.getName().equals(name))
          .findFirst().orElse(null);
      assertNotNull(foundSection);

      projectDao.delete(testProject.getId());
    }
  }

  @Test
  public void whenGetAll_filterByProjectId_returnsOnlySectionsInProject() {
    Project testProject = projectDao.create(CreateArgs.builder().name(projectName).build());
    for (String name : sectionNames) {
      sectionDao.create(
          ISectionDao.CreateArgs.builder().name(name).project_id(testProject.getId()).build());
    }

    Collection<Section> sections = sectionDao.getAll(testProject.getId());

    assertEquals(sectionNames.size(), sections.size());
    for (String name : sectionNames) {
      Section foundSection = sections.stream().filter(section -> section.getName().equals(name))
          .findFirst().orElse(null);
      assertNotNull(foundSection);

      projectDao.delete(testProject.getId());
    }
  }

  @Test
  public void whenCreate_sectionIsCreated() {
    Project testProject = projectDao.create(CreateArgs.builder().name(projectName).build());
    sectionDao.create(ISectionDao.CreateArgs.builder().name(sectionOne).project_id(
        testProject.getId()).build());

    Collection<Section> sections = sectionDao.getAll(testProject.getId());

    assertEquals(1, sections.size());
    Section section = sections.iterator().next();
    assertEquals(sectionOne, section.getName());
    assertEquals(testProject.getId(), section.getProject_id());

    projectDao.delete(testProject.getId());
  }

  @Test
  public void whenGet_correctSectionIsReturned() {
    Project testProject = projectDao.create(CreateArgs.builder().name(projectName).build());
    Section s1 = sectionDao.create(ISectionDao.CreateArgs.builder().name(sectionOne).project_id(
        testProject.getId()).build());
    Section s2 = sectionDao.create(ISectionDao.CreateArgs.builder().name(sectionTwo).project_id(
        testProject.getId()).build());

    Section queriedS1 = sectionDao.get(s1.getId());
    Section queriedS2 = sectionDao.get(s2.getId());

    assertEquals(s1, queriedS1);
    assertEquals(s2, queriedS2);

    projectDao.delete(testProject.getId());
  }

  @Test
  public void whenUpdate_sectionIsUpdated() {
    Project testProject = projectDao.create(CreateArgs.builder().name(projectName).build());
    Section s1 = sectionDao.create(ISectionDao.CreateArgs.builder().name(sectionOne).project_id(
        testProject.getId()).build());
    sectionDao.update(s1.getId(), sectionTwo);
    Section s2 = sectionDao.get(s1.getId());

    assertNotEquals(s1.getName(), s2.getName());
    assertEquals(sectionTwo, s2.getName());

    projectDao.delete(testProject.getId());
  }

  @Test
  public void whenDelete_sectionIsDeleted() {
    Project testProject = projectDao.create(CreateArgs.builder().name(projectName).build());
    Section s1 = sectionDao.create(ISectionDao.CreateArgs.builder().name(sectionOne).project_id(
        testProject.getId()).build());

    sectionDao.delete(s1.getId());
  }
}
