package threads;


public class DeleteDonationThread extends DatabaseThread implements Runnable{
    public int donationId;

    public DeleteDonationThread(int donationId){
        this.donationId = donationId;
    }
    @Override
    public void run() {
        super.deleteDonation(donationId);
    }
}
