package threads;

import domain.Project;

public class SaveNewProjectThread extends DatabaseThread implements Runnable{
    public Project project;

    public SaveNewProjectThread(Project project){
        this.project = project;
    }
    @Override
    public void run() {
        super.saveNewProject(project);
    }
}
