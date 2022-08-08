package com.rjesquivias.todoist.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.rjesquivias.todoist.domain.Colors;
import com.rjesquivias.todoist.util.http.HttpRequestFactory;
import java.util.Collection;

public interface IBaseDaoTest {

  String validUriString = "http://finance.yahoo.com/";
  String testToken = "test-token";
  Long testLabelId = Long.valueOf(1);
  Long testProjectId = Long.valueOf(19382);
  Long testSectionId = Long.valueOf(28391);
  Long testTaskId = Long.valueOf(1);
  Long testCommentId = Long.valueOf(1);
  String testFilter = "test-filter";
  String testLang = "en";
  String testContent = "testContent";
  String testDescription = "testDescription";
  Long testParentId = Long.valueOf(123);
  Long testParent = Long.valueOf(321);
  Long testOrder = Long.valueOf(382);
  Long testPriority = Long.valueOf(271);
  String testDueString = "testDueString";
  String testDueDate = "testDueDate";
  String testDueDatetime = "testDueDatetime";
  String testDueLang = "testDueLang";
  Long testAssignee = Long.valueOf(2819);
  String testName = "testName";
  Boolean testFavorite = true;
  Colors testColor = Colors.CHARCOAL;
  Collection<Long> testIds = ImmutableList.of(Long.valueOf(1), Long.valueOf(2),
      Long.valueOf(3),
      Long.valueOf(4), Long.valueOf(5));
  Collection<Long> testLabelIds = ImmutableList.of(Long.valueOf(1), Long.valueOf(2),
      Long.valueOf(3), Long.valueOf(4), Long.valueOf(5));
  HttpRequestFactory httpRequestFactory = new HttpRequestFactory(testToken,
      new ObjectMapper());
  ISectionDao.CreateArgs createSectionArgs = ISectionDao.CreateArgs.builder().name("test-section-1")
      .project_id(testProjectId).build();
  ITaskDao.CreateArgs createTaskArgs = ITaskDao.CreateArgs.builder().content(testContent)
      .description(testDescription).project_id(testProjectId).section_id(testSectionId)
      .parent_id(testParentId).parent(testParent).order(testOrder).label_ids(testLabelIds)
      .priority(testPriority).due_string(testDueString).due_date(testDueDate)
      .due_datetime(testDueDatetime).due_lang(testDueLang).assignee(testAssignee).build();
  ITaskDao.GetAllActiveArgs getAllActiveTaskArgs = ITaskDao.GetAllActiveArgs.builder()
      .project_id(testProjectId).section_id(testSectionId).label_id(testLabelId).filter(testFilter)
      .lang(testLang).ids(testIds).build();
  ICommentDao.CreateArgs createCommentArgs = ICommentDao.CreateArgs.builder().task_id(testTaskId)
      .content(testContent).build();
  ILabelDao.CreateArgs createLabelArgs = ILabelDao.CreateArgs.builder().name(testName)
      .order(testOrder).color(testColor).favorite(testFavorite).build();
  ILabelDao.UpdateArgs updateLabelArgs = ILabelDao.UpdateArgs.builder().name(testName)
      .order(testOrder).color(testColor).favorite(testFavorite).build();
  String testUpdatedSectionName = "test-new-section-name";

}
