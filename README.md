# Intro
RagTag is a Java client for the [Todoist REST API](https://developer.todoist.com/rest/v1/#overview).

# Usage Instructions
Code snippets provided below can be used as a quick start example of consuming the TodoistClient. 
For a more in depth understanding of the todoist api make sure you check out their [documentation](https://developer.todoist.com/guides/#developing-with-todoist).
## Working with Projects
```java
import com.rjesquivias.todoist.*;
public class Main {
    public static void main(String[] args) {
      TodoistClient client = TodoistClient.buildClient(".. your API token ..");
    
      Collection<Project> projects = client.getProjects();
    
      long projectId = your project id;
      Project project = client.getProject(projectId);
    
      Project newProject = client.createProject(Arguments.CreateProjectArgs.builder().name(".. your project name ..").build());
    
      client.updateProject(newProject.id(), Arguments.UpdateProjectArgs.builder().color(Color.BLUE).build());
    
      client.deleteProject(newProject.id());
    }
}
```