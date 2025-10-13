package threads;

import domain.Donor;
import domain.Organization;

public class SaveSuspendedUserInfoThread extends DatabaseThread implements Runnable{
    public Donor donor;
    public Organization organization;
    String reason;

    public SaveSuspendedUserInfoThread(Donor donor,Organization organization, String reason){
        this.donor = donor;
        this.organization = organization;
        this.reason = reason;
    }
    @Override
    public void run() {
        super.saveSuspendedUserInfo(donor,organization,reason);
    }
}
