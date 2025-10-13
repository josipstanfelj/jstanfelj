package com.example.donacijefx;

import domain.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import threads.*;
import utils.Utils;
import java.util.List;
import java.util.Optional;


public class CreateDonationController {
    @FXML
    private TextField donationAmountTextField;
    @FXML
    private ComboBox<String> projectComboBox;
    @FXML
    private TextArea messageTextArea;

    public void initialize(){
        GetAllProjectsThread getAllProjectsThread = new GetAllProjectsThread();
        Thread thread = new Thread(getAllProjectsThread);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Project> projectList = getAllProjectsThread.getProjectList();

        for(int i = 0; i < projectList.size();i++){
            projectComboBox.getItems().add(projectList.get(i).getProjectName());
        }
    }

    public void saveNewDonation() {
        GetAllSuspendedUsersThread getAllSuspendedUsersThread = new GetAllSuspendedUsersThread();
        Thread thread = new Thread(getAllSuspendedUsersThread);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SuspendedUsers suspendedUsers = getAllSuspendedUsersThread.getSuspendedUsers();
        Donor activeDonor = Utils.getActiveDonorUser();
        if(suspendedUsers.suspendedUsersMap.containsKey(activeDonor.getUserId())){
            showErrorDialog("Your account is currently suspended! Try Again Later!");
            return;
        }
        if(projectComboBox.getValue() == null){
            showErrorDialog("You have to choose which project you want to finance!");
            return;
        }else if(donationAmountTextField.getText().isEmpty() ) {
            showErrorDialog("You must enter the donation amount!");
            return;
        }

        String projectName = projectComboBox.getSelectionModel().getSelectedItem();

        GetAllProjectsThread getAllProjectsThread = new GetAllProjectsThread();
        Thread thread1 = new Thread(getAllProjectsThread);
        thread1.start();
        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Project> projects = getAllProjectsThread.getProjectList();

        Optional<Project> optionalProject = projects.stream()
                .filter(project -> projectName.equals(project.getProjectName()))
                .findFirst();

        if(optionalProject.isPresent()){
            if(!(isDouble(donationAmountTextField.getText()))){
                showErrorDialog("The entered value of the donation amount is not in a valid form!");
                return;
            }
            double donationAmount = Double.parseDouble(donationAmountTextField.getText());

            int activeUserId = Utils.getActiveDonorUserId();
            Donation newDonation = new Donation(donationAmount,activeUserId, optionalProject.get().getProjectId());

            SaveDonationThread saveDonationThread = new SaveDonationThread(newDonation);
            Thread thread2 = new Thread(saveDonationThread);
            thread2.start();
            try {
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            AddDonationToProjectThread addDonationToProjectThread = new AddDonationToProjectThread(newDonation,optionalProject.get().getProjectId());
            Thread thread3 = new Thread(addDonationToProjectThread);
            thread3.start();
            try {
                thread3.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            SaveMessageThread saveMessageThread = new SaveMessageThread(messageTextArea.getText(),optionalProject.get().getProjectId());
            Thread thread4 = new Thread(saveMessageThread);
            thread4.start();
            try {
                thread4.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            showInfoDialog("You have successfully sent a donation!");

            noteTheChange("Donation amount :" + donationAmount,"Donor");
        }else {
            showErrorDialog("Error occurred!");
            return;
        }

    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showInfoDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void noteTheChange(String name,String role){
        String typeOfChange = "Creating a donation.";
        String none = "None";
        NoteTheChange<String> change = new NoteTheChange<>(role,typeOfChange,none,name);
        Utils.saveChanges(change);
    }
}
