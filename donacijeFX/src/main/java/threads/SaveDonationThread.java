package threads;

import domain.Donation;

public class SaveDonationThread extends DatabaseThread implements Runnable{
    public Donation donation;

    public SaveDonationThread(Donation donation){
        this.donation = donation;
    }
    @Override
    public void run() {
        super.saveDonation(donation);
    }
}
