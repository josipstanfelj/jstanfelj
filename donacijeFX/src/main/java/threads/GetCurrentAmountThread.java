package threads;


public class GetCurrentAmountThread extends DatabaseThread implements Runnable {
    public double currentAmount;
    public int projectId ;

    public GetCurrentAmountThread(int projectId){
        this.projectId = projectId;
    }

    @Override
    public void run() {
        currentAmount = super.getCurrentAmount(projectId);
    }

    public double getCurrentAmount() {
        return currentAmount;
    }
}
