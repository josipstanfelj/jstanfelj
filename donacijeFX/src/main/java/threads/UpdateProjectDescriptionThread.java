package threads;

public class UpdateProjectDescriptionThread extends DatabaseThread implements Runnable{
    public int projectId;
    public String newDescription;

    public UpdateProjectDescriptionThread(int projectId, String newDescription){
        this.projectId = projectId;
        this.newDescription = newDescription;
    }

    @Override
    public void run() {
        super.updateProjectDescription(projectId,newDescription);
    }
}
