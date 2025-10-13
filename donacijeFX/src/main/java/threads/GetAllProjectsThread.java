package threads;

import domain.Project;

import java.util.List;

public class GetAllProjectsThread extends DatabaseThread implements Runnable{
    public List<Project> projectList;

    public GetAllProjectsThread(){}

    @Override
    public void run() {
        projectList = super.getAllProjects();
    }

    public List<Project> getProjectList() {
        return projectList;
    }
}
