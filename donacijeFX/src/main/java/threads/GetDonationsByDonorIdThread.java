package threads;

import domain.Donation;
import java.util.List;

public class GetDonationsByDonorIdThread extends DatabaseThread implements Runnable{
    public List<Donation> donationList;
    public int donorId ;

    public GetDonationsByDonorIdThread(int donorId){
        this.donorId = donorId;
    }

    @Override
    public void run() {
        donationList = super.getDonationsByDonorId(donorId);
    }

    public List<Donation> getDonationList() {
        return donationList;
    }
}
