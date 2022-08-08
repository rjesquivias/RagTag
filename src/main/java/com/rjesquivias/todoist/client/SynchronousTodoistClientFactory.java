package com.rjesquivias.todoist.client;

import com.rjesquivias.todoist.dao.CommentDao;
import com.rjesquivias.todoist.dao.LabelDao;
import com.rjesquivias.todoist.dao.ProjectDao;
import com.rjesquivias.todoist.dao.SectionDao;
import com.rjesquivias.todoist.dao.TaskDao;
import com.rjesquivias.todoist.util.http.HttpRequestHelper;
import io.github.cdimascio.dotenv.Dotenv;
import java.net.http.HttpClient;

public class SynchronousTodoistClientFactory {

  public static SynchronousTodoistClient buildClient() {
    HttpRequestHelper requestHelper = new HttpRequestHelper(HttpClient.newBuilder().build());
    Dotenv dotenv = Dotenv.load();
    return SynchronousTodoistClient.builder()
        .projectDao(new ProjectDao(HttpClient.newBuilder().build(), dotenv))
        .sectionDao(new SectionDao(requestHelper, dotenv))
        .taskDao(new TaskDao(requestHelper, dotenv))
        .commentDao(new CommentDao(requestHelper, dotenv))
        .labelDao(new LabelDao(requestHelper, dotenv)).build();
  }
}
