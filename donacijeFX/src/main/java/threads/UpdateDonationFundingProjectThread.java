package threads;


public class UpdateDonationFundingProjectThread extends DatabaseThread implements Runnable{
    public int donationId;
    public int newProjectId;

    public UpdateDonationFundingProjectThread(int donationId, int newProjectId){
        this.donationId = donationId;
        this.newProjectId = newProjectId;
    }

    @Override
    public void run() {
        super.updateDonationFundingProject(donationId,newProjectId);
    }
}
