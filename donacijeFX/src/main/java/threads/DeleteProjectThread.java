package threads;

public class DeleteProjectThread extends DatabaseThread implements Runnable{
    public int projectId;

    public DeleteProjectThread(int projectId){
        this.projectId = projectId;
    }
    @Override
    public void run() {
        super.deleteProject(projectId);
    }
}
