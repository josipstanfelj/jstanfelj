package threads;


public class SaveMessageThread extends DatabaseThread implements Runnable{
    public int projectId;
    public String message;

    public SaveMessageThread(String message,int projectId){
        this.projectId = projectId;
        this.message = message;
    }
    @Override
    public void run() {
        super.saveMessage(message,projectId);
    }
}
