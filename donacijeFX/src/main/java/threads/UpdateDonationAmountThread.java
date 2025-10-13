package threads;


public class UpdateDonationAmountThread extends DatabaseThread implements Runnable{
    public int donationId;
    public double donationAmount;

    public UpdateDonationAmountThread(int donationId, double donationAmount){
        this.donationId = donationId;
        this.donationAmount = donationAmount;
    }

    @Override
    public void run() {
        super.updateDonationAmount(donationId,donationAmount);
    }
}
