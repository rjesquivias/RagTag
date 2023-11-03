package com.rjesquivias.todoist;

import lombok.Builder;

import java.net.http.HttpClient;
import java.util.Collection;

@Builder
public class TodoistClient {

    public static final String REST_V_1_PROJECTS = "/rest/v1/projects/";
    public static final String REST_V_1_SECTIONS = "/rest/v1/sections/";
    public static final String REST_V_1_TASKS = "/rest/v1/tasks/";
    public static final String REST_V_1_COMMENTS = "/rest/v1/comments/";
    public static final String REST_V_1_LABELS = "/rest/v1/labels/";
    public static final String URL = "https://api.todoist.com";

    private final ICommentDao commentDao;
    private final ILabelDao labelDao;
    private final IProjectDao projectDao;
    private final ISectionDao sectionDao;
    private final ITaskDao taskDao;

    private static TodoistClient buildClient(String baseUrl, String apiToken) {
        HttpRequestHelper requestHelper = HttpRequestHelper.build(HttpClient.newBuilder().build());

        return TodoistClient.builder()
                .projectDao(new ProjectDao(requestHelper, baseUrl + REST_V_1_PROJECTS, apiToken))
                .sectionDao(new SectionDao(requestHelper, baseUrl + REST_V_1_SECTIONS, apiToken))
                .taskDao(new TaskDao(requestHelper, baseUrl + REST_V_1_TASKS, apiToken))
                .commentDao(new CommentDao(requestHelper, baseUrl + REST_V_1_COMMENTS, apiToken))
                .labelDao(new LabelDao(requestHelper, baseUrl + REST_V_1_LABELS, apiToken)).build();
    }

    public static TodoistClient buildClient(String apiToken) {
        return buildClient(URL, apiToken);
    }


    TodoistClient(ICommentDao commentDao, ILabelDao labelDao, IProjectDao projectDao,
                  ISectionDao sectionDao, ITaskDao taskDao) {
        this.commentDao = commentDao;
        this.labelDao = labelDao;
        this.projectDao = projectDao;
        this.sectionDao = sectionDao;
        this.taskDao = taskDao;
    }

    public Collection<Project> getProjects() { return projectDao.getAll(); }

    public Project getProject(long id) { return projectDao.get(id); }

    public Project createProject(Arguments.CreateProjectArgs args) {
        return projectDao.create(args);
    }

    public void updateProject(long id, Arguments.UpdateProjectArgs args) {
        projectDao.update(id, args);
    }

    public void deleteProject(long id) {
        projectDao.delete(id);
    }

    public Collection<Section> getSections() {
        return sectionDao.getAll();
    }

    public Collection<Section> getSectionsOfProject(long project_id) {
        return sectionDao.getAll(project_id);
    }

    public Section createSection(Arguments.CreateSectionArgs args) {
        return sectionDao.create(args);
    }

    public Section getSection(long id) {
        return sectionDao.get(id);
    }

    public void updateSection(long id, String name) {
        sectionDao.update(id, name);
    }

    public void deleteSection(long id) {
        sectionDao.delete(id);
    }

    public Collection<Task> getTasks(Arguments.GetAllActiveTasksArgs args) {
        return taskDao.getAllActive(args);
    }

    public Task createTask(Arguments.CreateTaskArgs args) {
        return taskDao.create(args);
    }

    public Task getTask(long id) {
        return taskDao.getActive(id);
    }

    public void updateTask(long id, Arguments.UpdateTaskArgs args) {
        taskDao.update(id, args);
    }

    public void closeTask(long id) {
        taskDao.close(id);
    }

    public void reOpenTask(long id) {
        taskDao.reOpen(id);
    }

    public void deleteTask(long id) {
        taskDao.delete(id);
    }

    public Collection<Comment> getCommentsOfProject(long projectId) { return commentDao.getAllInProject(projectId);}

    public Collection<Comment> getCommentsOfTask(long taskId) {
        return commentDao.getAllInTask(taskId);
    }

    public Comment createComment(Arguments.CreateCommentArgs args) {
        return commentDao.create(args);
    }

    public Comment getComment(long id) {
        return commentDao.get(id);
    }

    public void updateComment(long id, String content) {
        commentDao.update(id, content);
    }

    public void deleteComment(long id) {
        commentDao.delete(id);
    }

    public Collection<Label> getLabels() {
        return labelDao.getAll();
    }

    public Label createLabel(Arguments.CreateLabelArgs args) {
        return labelDao.create(args);
    }

    public Label getLabel(long id) { return labelDao.get(id);}

    public void updateLabel(long id, Arguments.UpdateLabelArgs args) {
        labelDao.update(id, args);
    }

    public void deleteLabel(long id) {
        labelDao.delete(id);
    }
}
