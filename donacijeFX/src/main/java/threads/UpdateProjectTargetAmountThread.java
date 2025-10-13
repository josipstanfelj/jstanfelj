package threads;

public class UpdateProjectTargetAmountThread extends DatabaseThread implements Runnable{
    public int projectId;
    public double newTargetAmount;

    public UpdateProjectTargetAmountThread(int projectId, double newTargetAmount){
        this.projectId = projectId;
        this.newTargetAmount = newTargetAmount;
    }

    @Override
    public void run() {
        super.updateProjectTargetAmount(projectId,newTargetAmount);
    }
}
