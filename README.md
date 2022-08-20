# Intro

RagTag is a Java client for the [Todoist REST API](https://developer.todoist.com/rest/v1/#overview).

# Getting started

## Installation

This project is released on maven. You can also download the most recent release if you plan on
building from source.

## Examples

Code snippets provided below can be used as a quick start example of consuming the TodoistClient.
For a more in depth understanding of the todoist api make sure you check out
their [documentation](https://developer.todoist.com/guides/#developing-with-todoist).

## Working with Projects

The following examples are provided in this sample.

1. Getting all projects associated with an account
2. Getting a specific project
3. Creating a new project
4. Updating a project
5. Deleting a project

```java
import com.rjesquivias.todoist.*;

public class Main {

  public static void main(String[] args) {
    TodoistClient client = TodoistClient.buildClient(".. your API token ..");

    Collection<Project> projects = client.getProjects();

    long projectId = your project id;
    Project project = client.getProject(projectId);

    Project newProject = client.createProject(
        Arguments.CreateProjectArgs.builder().name(".. your project name ..").build());

    client.updateProject(newProject.id(),
        Arguments.UpdateProjectArgs.builder().color(Color.BLUE).build());

    client.deleteProject(newProject.id());
  }
}
```

## Working with Sections

An example of adding a section to a newly created project.

```java
import com.rjesquivias.todoist.*;

public class Main {

  public static void main(String[] args) {
    TodoistClient client = TodoistClient.buildClient(".. your API token ..");

    Project project = client.createProject(
        CreateProjectArgs.builder().name("my new project").build());

    client.createSection(
        CreateSectionArgs.builder().project_id(project.id()).name("my new section").build());

    client.deleteProject(project.id());
  }
}
```

## Working with Tasks

An example of creating and deleting a new task.

```java
import com.rjesquivias.todoist.*;

public class Main {

  public static void main(String[] args) {
    TodoistClient client = TodoistClient.buildClient(".. your API token ..");

    Task task = client.createTask(CreateTaskArgs.builder().content("my new task").build());

    client.deleteTask(task.id());
  }
}
```

## Working with Labels

An example of applying a newly created label to a new task.

```java
import com.rjesquivias.todoist.*;

public class Main {

  public static void main(String[] args) {
    TodoistClient client = TodoistClient.buildClient(".. your API token ..");

    Task task = client.createTask(CreateTaskArgs.builder().content("my new task").build());

    Label label = client.createLabel(CreateLabelArgs.builder().name("new label").build());
    client.updateTask(task.id(), UpdateTaskArgs.builder().label_ids(List.of(label.id())).build());

    client.deleteLabel(label.id());
    client.deleteTask(task.id());
  }
}
```

## Working with Comments

An example of adding a comment to a newly created task.

```java
import com.rjesquivias.todoist.*;

public class Main {

  public static void main(String[] args) {
    TodoistClient client = TodoistClient.buildClient(".. your API token ..");

    Task task = client.createTask(CreateTaskArgs.builder().content("my new task").build());

    client.createComment(
        CreateCommentArgs.builder().task_id(task.id()).content("my comment").build());

    client.deleteTask(task.id());
  }
}
```