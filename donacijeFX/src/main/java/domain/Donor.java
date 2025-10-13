package domain;

import java.util.ArrayList;
import java.util.List;

public non-sealed class Donor extends User implements AccAndProjectManagement {

    private String donorName;
    private String donorLastName;
    private DonorInfo info;
    private List<Donation> myDonations;
    public boolean donationActivity;

    public Donor(String userName, String passwordHashCode, String name, String lastname, DonorInfo info, int donorId) {
        super(userName,passwordHashCode,donorId);
        this.myDonations = new ArrayList<>();
        this.donorName = name;
        this.donorLastName = lastname;
        this.info = info;
    }

    public Donor(String userName, String password){
        super(userName, password);
    }

    public void setDonationActivity(boolean donationActivity) {
        this.donationActivity = donationActivity;
    }


    public String getDonorName() {
        return donorName;
    }

    public String getDonorLastname() {
        return donorLastName;
    }

    public DonorInfo getInfo() {
        return info;
    }

    public List<Donation> getMyDonations() {
        return myDonations;
    }
    public void setDonorName(String name) {
        this.donorName = name;
    }

    public void setLastname(String lastname) {
        this.donorLastName = lastname;
    }

    public void setInfo(DonorInfo info) {
        this.info = info;
    }

    public void setMyDonations(List<Donation> myDonations) {
        this.myDonations = myDonations;
    }

    @Override
    public void addDonation(Donation donation) {
        myDonations.add(donation);
    }

    public String getDonorUserName(){
        return super.getUserName();
    }
    public String getDonorPasswordHashCode(){
        return super.getPasswordHashCode();
    }

    @Override
    public String toString(){
        return "User: " + super.getUserName() + " ,Name: "+ getDonorName() +" "+getDonorLastname();
    }
}
