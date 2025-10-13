package threads;

import domain.Donation;

public class AddDonationAmountToProjectThread extends DatabaseThread implements Runnable{
    public Donation donation;
    public int projectId ;

    public AddDonationAmountToProjectThread(Donation donation,int projectId){
        this.donation = donation;
        this.projectId = projectId;
    }
    @Override
    public void run() {
        super.addDonationAmountToProject(donation,projectId);
    }
}
