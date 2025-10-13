package threads;

import domain.Donor;

public class SaveDonorThread extends DatabaseThread implements Runnable{
    public Donor donor;

    public SaveDonorThread(Donor donor){
        this.donor = donor;
    }
    @Override
    public void run() {
        super.saveDonor(donor);
    }
}
