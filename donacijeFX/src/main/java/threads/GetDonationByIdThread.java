package threads;

import domain.Donation;

public class GetDonationByIdThread extends DatabaseThread implements Runnable{
    public Donation donation;
    public int donationId ;

    public GetDonationByIdThread(int donationId){
        this.donationId = donationId;
    }

    @Override
    public void run() {
        donation = super.getDonationById(donationId);
    }

    public Donation getDonation() {
        return donation;
    }
}
