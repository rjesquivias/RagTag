package com.rjesquivias.todoist;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collection;
import java.util.List;

public final class TestConstants {

  private TestConstants() {}

  static final String validUriString = "http://finance.yahoo.com/";
  static final String testToken = "test-token";
  static final Long testLabelId = 1L;
  static final Long testProjectId = 19382L;
  static final Long testSectionId = 28391L;
  static final Long testTaskId = 1L;
  static final Long testCommentId = 1L;
  static final String testFilter = "test-filter";
  static final String testLang = "en";
  static final String testContent = "testContent";
  static final String testDescription = "testDescription";
  static final Long testParentId = 123L;
  static final Long testParent = 321L;
  static final Long testOrder = 382L;
  static final Long testPriority = 271L;
  static final String testDueString = "testDueString";
  static final String testDueDate = "testDueDate";
  static final String testDueDatetime = "testDueDatetime";
  static final String testDueLang = "testDueLang";
  static final Long testAssignee = 2819L;
  static final String testName = "testName";
  static final Boolean testFavorite = true;
  static final Color testColor = Color.CHARCOAL;
  static final Collection<Long> testIds = List.of(1L, 2L,
          3L,
          4L, 5L);
  static final Collection<Long> testLabelIds = List.of(1L, 2L,
          3L, 4L, 5L);
  static final HttpRequestFactory httpRequestFactory = new HttpRequestFactory(testToken,
      new ObjectMapper());
  static final Arguments.CreateSectionArgs createSectionArgs = Arguments.CreateSectionArgs.builder().name("test-section-1")
      .project_id(testProjectId).build();
  static final Arguments.CreateTaskArgs createTaskArgs = Arguments.CreateTaskArgs.builder().content(testContent)
      .description(testDescription).project_id(testProjectId).section_id(testSectionId)
      .parent_id(testParentId).parent(testParent).order(testOrder).label_ids(testLabelIds)
      .priority(testPriority).due_string(testDueString).due_date(testDueDate)
      .due_datetime(testDueDatetime).due_lang(testDueLang).assignee(testAssignee).build();
  static final Arguments.GetAllActiveTasksArgs getAllActiveTaskArgs = Arguments.GetAllActiveTasksArgs.builder()
      .project_id(testProjectId).section_id(testSectionId).label_id(testLabelId).filter(testFilter)
      .lang(testLang).ids(testIds).build();
  static final Arguments.CreateCommentArgs createCommentArgs = Arguments.CreateCommentArgs.builder().task_id(testTaskId)
      .content(testContent).build();
  static final Arguments.CreateLabelArgs createLabelArgs = Arguments.CreateLabelArgs.builder().name(testName)
      .order(testOrder).color(testColor).favorite(testFavorite).build();
  static final Arguments.UpdateLabelArgs updateLabelArgs = Arguments.UpdateLabelArgs.builder().name(testName)
      .order(testOrder).color(testColor).favorite(testFavorite).build();
  static final Arguments.CreateProjectArgs createProjectArgs = Arguments.CreateProjectArgs.builder().name(testName)
      .build();
  static final Arguments.UpdateProjectArgs updateProjectArgs = Arguments.UpdateProjectArgs.builder().name(testName)
      .build();
  static final Arguments.UpdateTaskArgs updateTaskArgs = Arguments.UpdateTaskArgs.builder().content(testContent).build();

  static final String testUpdatedSectionName = "test-new-section-name";

}
