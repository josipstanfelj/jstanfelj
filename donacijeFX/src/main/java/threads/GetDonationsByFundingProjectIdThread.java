package threads;

import domain.Donation;
import java.util.List;

public class GetDonationsByFundingProjectIdThread extends DatabaseThread implements Runnable{
    public List<Donation> donationList;
    public int projectId ;

    public GetDonationsByFundingProjectIdThread(int projectId){
        this.projectId = projectId;
    }

    @Override
    public void run() {
        donationList = super.getDonationsByFundingProjectId(projectId);
    }

    public List<Donation> getDonationList() {
        return donationList;
    }
}
