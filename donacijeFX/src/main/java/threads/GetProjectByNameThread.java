package threads;

import domain.Project;

import java.util.Optional;

public class GetProjectByNameThread extends DatabaseThread implements Runnable {
    public Optional<Project> projectOptional;
    public String projectName ;

    public GetProjectByNameThread(String projectName){
        this.projectName = projectName;
    }

    @Override
    public void run() {
       projectOptional = super.getProjectByName(projectName);
    }

    public Optional<Project> getProjectOptional() {
        return projectOptional;
    }
}
