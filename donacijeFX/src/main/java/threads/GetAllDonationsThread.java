package threads;

import domain.Donation;
import java.util.List;

public class GetAllDonationsThread extends DatabaseThread implements Runnable{
    public List<Donation> donationList;

    public GetAllDonationsThread(){}

    @Override
    public void run() {
        donationList = super.getAllDonations();
    }

    public List<Donation> getDonationList() {
        return donationList;
    }
}
