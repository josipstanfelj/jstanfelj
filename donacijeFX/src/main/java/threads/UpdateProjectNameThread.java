package threads;

public class UpdateProjectNameThread extends DatabaseThread implements Runnable{

    public int projectId;
    public String newName;

    public UpdateProjectNameThread(int projectId, String newName){
        this.projectId = projectId;
        this.newName = newName;
    }

    @Override
    public void run() {
        super.updateProjectName(projectId,newName);
    }
}
