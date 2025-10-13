package threads;

import domain.Donation;

public class UpdateProjectCurrentAmountAfterEditingDonationThread extends DatabaseThread implements Runnable{
    public Donation donation;
    public double newAmount;

    public UpdateProjectCurrentAmountAfterEditingDonationThread(Donation donation,double newAmount){
        this.donation = donation;
        this.newAmount = newAmount;
    }

    @Override
    public void run() {
        super.updateProjectCurrentAmountAfterEditingDonation(donation,newAmount);
    }
}
