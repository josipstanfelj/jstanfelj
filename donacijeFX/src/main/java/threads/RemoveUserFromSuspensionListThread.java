package threads;

import domain.Donor;


public class RemoveUserFromSuspensionListThread extends DatabaseThread implements Runnable{
    public Donor donor;

    public RemoveUserFromSuspensionListThread(Donor donor){
        this.donor = donor;
    }

    @Override
    public void run() {
        super.removeUserFromSuspensionList(donor);
    }
}
