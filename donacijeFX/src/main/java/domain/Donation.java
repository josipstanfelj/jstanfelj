package domain;



import database.Database;
import enumm.Fees;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Donation implements Costs {
    private final double donationAmount;

    private final LocalDateTime creationDate;

    private int whoIsDonorId;

    private int FundedProjectId;

    public int donationId;

    public Donation(int donationId, double donationAmount, int whatIsDonorId, int whatIsFundedProjectId) {
        this.donationId = donationId;
        this.donationAmount = donationAmount;
        this.whoIsDonorId = whatIsDonorId;
        this.FundedProjectId = whatIsFundedProjectId;
        this.creationDate = LocalDateTime.now();
    }

    public Donation(int donationId,double donationAmount,LocalDateTime localDateTime, int whatIsDonorId, int whatIsFundedProjectId) {
        this.donationId = donationId;
        this.donationAmount = donationAmount;
        this.whoIsDonorId = whatIsDonorId;
        this.FundedProjectId = whatIsFundedProjectId;
        this.creationDate = localDateTime;
    }
    public Donation(double donationAmount, int whatIsDonorId, int whatIsFundedProjectId) {
        this.donationAmount = donationAmount;
        this.whoIsDonorId = whatIsDonorId;
        this.FundedProjectId = whatIsFundedProjectId;
        this.creationDate = LocalDateTime.now();
    }

    public Donation(double donationAmount) {
        this.donationAmount = donationAmount;
        this.creationDate = LocalDateTime.now();

    }
    @Override
    public String toString(){
        Project project = Database.getProjectById(FundedProjectId);
        return "Donation amount : " + donationAmount + "  Date: " + creationDate + "  Project : " + project.getProjectName();
    }
    public double getDonationAmount() {
        return donationAmount;
    }
    @Override
    public double getAdministrativeCosts(){
        return donationAmount * Fees.ADMINISTRATIVE_COST.getPercentage();
    }
    @Override
    public double getGovernmentTax(){
        return donationAmount * Fees.GOVERNMENT_TAX.getPercentage();
    }
    @Override
    public double getTransactionCosts(){
        return donationAmount * Fees.TRANSACTION_COST.getPercentage();
    }

    public double  getAllCosts(){
        return getGovernmentTax() + getAdministrativeCosts() + getTransactionCosts();
    }

    public double getReceivedDonationAmount(){
        return donationAmount - getAllCosts();
    }

    public int whatIsDonorId() {
        return whoIsDonorId;
    }

    public int whatIsFundedProjectId() {
        return FundedProjectId;
    }

    public String getDonationDateAndTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return creationDate.format(dateTimeFormatter);
    }

    public int getDonationId() {
        return donationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Donation donation = (Donation) o;
        return Double.compare(donationAmount, donation.donationAmount) == 0 && whoIsDonorId == donation.whoIsDonorId && FundedProjectId == donation.FundedProjectId && donationId == donation.donationId && Objects.equals(creationDate, donation.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(donationAmount, creationDate, whoIsDonorId, FundedProjectId, donationId);
    }
}