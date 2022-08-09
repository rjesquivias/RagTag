package com.rjesquivias.todoist;

import com.rjesquivias.todoist.domain.*;
import lombok.Builder;

import java.net.http.HttpClient;
import java.util.Collection;

@Builder
public class TodoistClient {

    private final ICommentDao commentDao;
    private final ILabelDao labelDao;
    private final IProjectDao projectDao;
    private final ISectionDao sectionDao;
    private final ITaskDao taskDao;

    public static TodoistClient buildClient(String baseUrl, String apiToken) {
        HttpRequestHelper requestHelper = new HttpRequestHelper(HttpClient.newBuilder().build());

        return TodoistClient.builder()
                .projectDao(new ProjectDao(HttpClient.newBuilder().build(), baseUrl + "/rest/v1/projects/", apiToken))
                .sectionDao(new SectionDao(requestHelper, baseUrl + "/rest/v1/sections/", apiToken))
                .taskDao(new TaskDao(requestHelper, baseUrl + "/rest/v1/tasks/", apiToken))
                .commentDao(new CommentDao(requestHelper, baseUrl + "/rest/v1/comments/", apiToken))
                .labelDao(new LabelDao(requestHelper, baseUrl + "/rest/v1/labels/", apiToken)).build();
    }

    public static TodoistClient buildClient(String apiToken) {
        return buildClient("https://api.todoist.com", apiToken);
    }


    TodoistClient(ICommentDao commentDao, ILabelDao labelDao, IProjectDao projectDao,
                  ISectionDao sectionDao, ITaskDao taskDao) {
        this.commentDao = commentDao;
        this.labelDao = labelDao;
        this.projectDao = projectDao;
        this.sectionDao = sectionDao;
        this.taskDao = taskDao;
    }

    public Collection<Project> getProjects() {
        return this.projectDao.getAll();
    }

    public Project getProject(long id) {
        return this.projectDao.get(id);
    }

    public Project createProject(IProjectDao.CreateArgs args) {
        return this.projectDao.create(args);
    }

    public void updateProject(long id, IProjectDao.UpdateArgs args) {
        this.projectDao.update(id, args);
    }

    public void deleteProject(long id) {
        this.projectDao.delete(id);
    }

    public Collection<Section> getSections() {
        return this.sectionDao.getAll();
    }

    public Collection<Section> getSectionsOfProject(long project_id) {
        return this.sectionDao.getAll(project_id);
    }

    public Section createSection(ISectionDao.CreateArgs args) {
        return this.sectionDao.create(args);
    }

    public Section getSection(long id) {
        return this.sectionDao.get(id);
    }

    public void updateSection(long id, String name) {
        this.sectionDao.update(id, name);
    }

    public void deleteSection(long id) {
        this.sectionDao.delete(id);
    }

    public Collection<Task> getTasks(ITaskDao.GetAllActiveArgs args) {
        return this.taskDao.getAllActive(args);
    }

    public Task createTask(ITaskDao.CreateArgs args) {
        return this.taskDao.create(args);
    }

    public Task getTask(long id) {
        return this.taskDao.getActive(id);
    }

    public void updateTask(long id, ITaskDao.UpdateArgs args) {
        this.taskDao.update(id, args);
    }

    public void closeTask(long id) {
        this.taskDao.close(id);
    }

    public void reOpenTask(long id) {
        this.taskDao.reOpen(id);
    }

    public void deleteTask(long id) {
        this.taskDao.delete(id);
    }

    public Collection<Comment> getCommentsOfProject(long projectId) {
        return this.commentDao.getAllInProject(projectId);
    }

    public Collection<Comment> getCommentsOfTask(long taskId) {
        return this.commentDao.getAllInTask(taskId);
    }

    public Comment createComment(ICommentDao.CreateArgs args) {
        return this.commentDao.create(args);
    }

    public Comment getComment(long id) {
        return this.commentDao.get(id);
    }

    public void updateComment(long id, String content) {
        this.commentDao.update(id, content);
    }

    public void deleteComment(long id) {
        this.commentDao.delete(id);
    }

    public Collection<Label> getLabels() {
        return this.labelDao.getAll();
    }

    public Label createLabel(ILabelDao.CreateArgs args) {
        return this.labelDao.create(args);
    }

    public Label getLabel(long id) {
        return this.labelDao.get(id);
    }

    public void updateLabel(long id, ILabelDao.UpdateArgs args) {
        this.labelDao.update(id, args);
    }

    public void deleteLabel(long id) {
        this.labelDao.delete(id);
    }
}
