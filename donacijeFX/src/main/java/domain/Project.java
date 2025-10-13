package domain;

import java.util.List;
import java.util.Objects;

public final class Project implements AccAndProjectManagement {
    private int projectId;
    private String projectName;
    private String description;
    private double targetAmount;
    private double currentAmount;
    private List<Donation> donations;
    // mozda iskoristiti mapu?


    public Project(String projectName, String description, double targetAmount, List<Donation> donations) {
        this.projectName = projectName;
        this.description = description;
        this.targetAmount = targetAmount;
        this.currentAmount = 0;
        this.donations = donations;
    }

    public Project(String projectName, String description, double targetAmount) {
        this.projectName = projectName;
        this.description = description;
        this.currentAmount = 0;
        this.targetAmount = targetAmount;
    }


    public Project(int projectId, String projectName, String description, double targetAmount, double currentAmount, List<Donation> donations) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.description = description;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.donations = donations;
    }
    public Project(){}

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    @Override
    public void addDonation(Donation donation) {
        donations.add(donation);
        currentAmount = currentAmount + donation.getReceivedDonationAmount();
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public void setDonations(List<Donation> donations) {
        this.donations = donations;
    }

    @Override
    public String toString(){
        return "Project name:  " + projectName + ", Description :  " + description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return projectId == project.projectId && Double.compare(targetAmount, project.targetAmount) == 0 && Double.compare(currentAmount, project.currentAmount) == 0 && Objects.equals(projectName, project.projectName) && Objects.equals(description, project.description) && Objects.equals(donations, project.donations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, projectName, description, targetAmount, currentAmount, donations);
    }
}
