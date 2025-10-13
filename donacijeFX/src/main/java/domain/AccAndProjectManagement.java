package domain;

public sealed interface AccAndProjectManagement permits Project,Donor{
    public void addDonation(Donation donation);
}
