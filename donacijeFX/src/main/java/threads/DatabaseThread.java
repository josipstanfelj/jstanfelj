package threads;

import database.Database;
import domain.*;
import java.util.List;
import java.util.Optional;

public class DatabaseThread {

    public static Boolean activeConnectionWithDatabase = false;

    public synchronized void saveDonation(Donation donation) {

        while(activeConnectionWithDatabase){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        activeConnectionWithDatabase = true;

        Database.saveDonation(donation);

        activeConnectionWithDatabase = false;
        notifyAll();
    }

    public synchronized void saveDonor(Donor donor) {

        while(activeConnectionWithDatabase){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        activeConnectionWithDatabase = true;

        Database.saveDonor(donor);

        activeConnectionWithDatabase = false;
        notifyAll();
    }
    public synchronized void saveOrganization(Organization organization) {

        while(activeConnectionWithDatabase){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        activeConnectionWithDatabase = true;

        Database.saveOrganization(organization);

        activeConnectionWithDatabase = false;
        notifyAll();
    }
    public synchronized void saveMessage(String message,int projectId) {

        while(activeConnectionWithDatabase){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        activeConnectionWithDatabase = true;

        Database.saveMessage(message,projectId);

        activeConnectionWithDatabase = false;
        notifyAll();
    }


    public synchronized void addDonationToProject(Donation donation,int projectId) {

        while(activeConnectionWithDatabase){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        activeConnectionWithDatabase = true;

        Database.addDonationToProject(donation,projectId);

        activeConnectionWithDatabase = false;
        notifyAll();
    }

    public synchronized List<Donation> getDonationsByDonorId(int checkDonorId) {

        while(activeConnectionWithDatabase){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        activeConnectionWithDatabase = true;

        List<Donation> donationList = Database.getDonationsByDonorId(checkDonorId);

        activeConnectionWithDatabase = false;
        notifyAll();
        return donationList;
    }

    public synchronized List<Donation> getDonationsByFundingProjectId(int checkFundingProjectId){

        while(activeConnectionWithDatabase){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        activeConnectionWithDatabase = true;

        List<Donation> donationList = Database.getDonationsByFundingProjectId(checkFundingProjectId);

        activeConnectionWithDatabase = false;
        notifyAll();
        return donationList;
    }

    public synchronized List<Donation> getAllDonations() {

        while(activeConnectionWithDatabase){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        activeConnectionWithDatabase = true;

        List<Donation> donationList = Database.getAllDonations();

        activeConnectionWithDatabase = false;
        notifyAll();
        return donationList;
    }
    public synchronized List<Project> getAllProjects() {

        while(activeConnectionWithDatabase){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        activeConnectionWithDatabase = true;

        List<Project> projectList = Database.getAllProjects();

        activeConnectionWithDatabase = false;
        notifyAll();
        return projectList;
    }

    public synchronized void saveNewProject(Project project) {

        while(activeConnectionWithDatabase){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        activeConnectionWithDatabase = true;

        Database.saveNewProject(project);

        activeConnectionWithDatabase = false;
        notifyAll();
    }

    public synchronized void deleteDonation(int donationId) {

        while(activeConnectionWithDatabase){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        activeConnectionWithDatabase = true;

        Database.deleteDonation(donationId);

        activeConnectionWithDatabase = false;

        notifyAll();
    }
    public synchronized void deleteProject(int projectId) {

        while(activeConnectionWithDatabase){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        activeConnectionWithDatabase = true;

        Database.deleteProject(projectId);

        activeConnectionWithDatabase = false;
        notifyAll();
    }

    public synchronized double getCurrentAmount(int projectId) {

        while(activeConnectionWithDatabase){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        activeConnectionWithDatabase = true;

        double currentAmount = Database.getCurrentAmount(projectId);

        activeConnectionWithDatabase = false;
        notifyAll();
        return currentAmount;
    }
    public synchronized Project getProjectById(int checkId) {

        while(activeConnectionWithDatabase){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        activeConnectionWithDatabase = true;

        Project project = Database.getProjectById(checkId);

        activeConnectionWithDatabase = false;
        notifyAll();
        return project;
    }

    public synchronized Donation getDonationById(int checkId) {

        while(activeConnectionWithDatabase){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        activeConnectionWithDatabase = true;

        Donation donation = Database.getDonationById(checkId);

        activeConnectionWithDatabase = false;
        notifyAll();
        return donation;
    }

    public synchronized Optional<Project> getProjectByName(String name) {

        while(activeConnectionWithDatabase){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        activeConnectionWithDatabase = true;

        Optional<Project> optionalProject = Database.getProjectByName(name);

        activeConnectionWithDatabase = false;
        notifyAll();
        return optionalProject;
    }

    public synchronized void updateDonationFundingProject(int donationId, int newProjectId) {

        while(activeConnectionWithDatabase){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        activeConnectionWithDatabase = true;

        Database.updateDonationFundingProject(donationId,newProjectId);

        activeConnectionWithDatabase = false;
        notifyAll();
    }


    public synchronized void updateDonationAmount(int donationId, double donationAmount) {

        while(activeConnectionWithDatabase){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        activeConnectionWithDatabase = true;

        Database.updateDonationAmount(donationId,donationAmount);

        activeConnectionWithDatabase = false;
        notifyAll();
    }
    public synchronized void updateProjectName(int projectId, String newName) {

        while(activeConnectionWithDatabase){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        activeConnectionWithDatabase = true;

        Database.updateProjectName(projectId,newName);

        activeConnectionWithDatabase = false;
        notifyAll();
    }
    public synchronized void updateProjectDescription(int projectId, String newDescription) {

        while(activeConnectionWithDatabase){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        activeConnectionWithDatabase = true;

        Database.updateProjectDescription(projectId,newDescription);

        activeConnectionWithDatabase = false;
        notifyAll();
    }
    public synchronized void updateProjectTargetAmount(int projectId, double newTargetAmount) {

        while(activeConnectionWithDatabase){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        activeConnectionWithDatabase = true;

        Database.updateProjectTargetAmount(projectId,newTargetAmount);

        activeConnectionWithDatabase = false;
        notifyAll();
    }
    public synchronized void updateProjectCurrentAmountAfterDeletingDonation(Donation donation){

        while(activeConnectionWithDatabase){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        activeConnectionWithDatabase = true;

        Database.updateProjectCurrentAmountAfterDeletingDonation(donation);

        activeConnectionWithDatabase = false;
        notifyAll();
    }
    public synchronized void updateProjectCurrentAmountAfterEditingDonation(Donation donation, double newAmount) {

        while(activeConnectionWithDatabase){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        activeConnectionWithDatabase = true;

        Database.updateProjectCurrentAmountAfterEditingDonation(donation,newAmount);

        activeConnectionWithDatabase = false;
        notifyAll();
    }
    public synchronized void addDonationAmountToProject(Donation donation, int projectId) {

        while(activeConnectionWithDatabase){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        activeConnectionWithDatabase = true;

        Database.addDonationAmountToProject(donation,projectId);

        activeConnectionWithDatabase = false;
        notifyAll();
    }
    public synchronized void saveSuspendedUserInfo(Donor donor, Organization organization, String reason) {

        while(activeConnectionWithDatabase){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        activeConnectionWithDatabase = true;

        Database.saveSuspendedUserInfo(donor,organization,reason);

        activeConnectionWithDatabase = false;
        notifyAll();
    }
    public synchronized void removeUserFromSuspensionList(Donor donor) {

        while(activeConnectionWithDatabase){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        activeConnectionWithDatabase = true;

        Database.removeUserFromSuspensionList(donor);

        activeConnectionWithDatabase = false;
        notifyAll();
    }
    public synchronized SuspendedUsers getAllSuspendedUsers() {

        while(activeConnectionWithDatabase){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        activeConnectionWithDatabase = true;

        SuspendedUsers suspendedUsers = Database.getAllSuspendedUsers();

        activeConnectionWithDatabase = false;
        notifyAll();
        return suspendedUsers;
    }






}
