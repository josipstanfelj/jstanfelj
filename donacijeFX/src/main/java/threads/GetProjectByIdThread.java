package threads;

import domain.Project;

public class GetProjectByIdThread extends DatabaseThread implements Runnable{
    public Project project;
    public int projectId ;

    public GetProjectByIdThread(int projectId){
        this.projectId = projectId;
    }

    @Override
    public void run() {
        project = super.getProjectById(projectId);
    }

    public Project getProject() {
        return project;
    }
}
