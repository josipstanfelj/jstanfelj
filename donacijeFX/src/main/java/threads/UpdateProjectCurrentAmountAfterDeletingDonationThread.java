package threads;

import domain.Donation;

public class UpdateProjectCurrentAmountAfterDeletingDonationThread extends DatabaseThread implements Runnable{
    public Donation donation;

    public UpdateProjectCurrentAmountAfterDeletingDonationThread(Donation donation){
        this.donation = donation;
    }

    @Override
    public void run() {
        super.updateProjectCurrentAmountAfterDeletingDonation(donation);
    }
}
