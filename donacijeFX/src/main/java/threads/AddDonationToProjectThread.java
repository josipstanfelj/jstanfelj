package threads;

import domain.Donation;

public class AddDonationToProjectThread extends DatabaseThread implements Runnable{
    public Donation donation;
    public int projectId ;

    public AddDonationToProjectThread(Donation donation,int projectId){
        this.donation = donation;
        this.projectId = projectId;
    }
    @Override
    public void run() {
        super.addDonationToProject(donation,projectId);
    }

}
