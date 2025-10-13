package com.example.donacijefx;

import Exceptions.MissingSelectionException;
import database.Database;
import domain.Donation;
import domain.NoteTheChange;
import domain.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import threads.*;
import utils.Utils;

import java.util.Optional;

public class EditDonationOrgController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);


    @FXML
    private ListView<Donation> itemListView;

    @FXML
    private CheckBox projectNameCheckBox;

    @FXML
    private CheckBox amountCheckBox;
    @FXML
    private TextField newProjectNameTextField;

    @FXML
    private TextField newProjectAmountTextField;

    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }



    public void initialize(){
        GetAllDonationsThread getAllDonationsThread = new GetAllDonationsThread();
        Thread thread = new Thread(getAllDonationsThread);
        thread.start();
        waitThread(thread);
        ObservableList<Donation> donationList = FXCollections.observableArrayList(getAllDonationsThread.getDonationList());
        itemListView.setItems(donationList);
        itemListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }
    public void updateData(){
        GetAllDonationsThread getAllDonationsThread = new GetAllDonationsThread();
        Thread thread = new Thread(getAllDonationsThread);
        thread.start();
        waitThread(thread);
        ObservableList<Donation> donationList = FXCollections.observableArrayList(getAllDonationsThread.getDonationList());
        itemListView.setItems(donationList);
    }

    public void updateDonation(){
        try{
            if(itemListView.getSelectionModel().isEmpty()){
                throw new MissingSelectionException("You have to choose which donation you want to edit!");
            }
        }catch (MissingSelectionException ex){
            showErrorInfoDialog(ex.getMessage());
            logger.info(ex.getMessage());
            return;
        }

        if(!projectNameCheckBox.isSelected() && !amountCheckBox.isSelected()){
            showErrorInfoDialog("Mark the checkboxes to be able to save the data!");
            return;
        }
        Donation donation = itemListView.getSelectionModel().getSelectedItem();
        if(showConfirmationDialog("Are you sure you want make this changes?")){

            if(projectNameCheckBox.isSelected() && amountCheckBox.isSelected()){
                String newProjectName = newProjectNameTextField.getText();
                String newAmountString = newProjectAmountTextField.getText();
                GetProjectByIdThread getProjectByIdThread = new GetProjectByIdThread(donation.whatIsFundedProjectId());
                Thread thread = new Thread(getProjectByIdThread);
                thread.start();
                waitThread(thread);
                Project oldProject = getProjectByIdThread.getProject();
                GetProjectByNameThread getProjectByNameThread = new GetProjectByNameThread(newProjectName);
                Thread thread1 = new Thread(getProjectByNameThread);
                thread1.start();
                waitThread(thread1);
                Optional<Project> optionalProject = getProjectByNameThread.projectOptional;

                double newAmount = 0;

                if (isDouble(newAmountString) && optionalProject.isPresent()) {
                    newAmount = Double.parseDouble(newAmountString);
                    UpdateProjectCurrentAmountAfterDeletingDonationThread updateAmountThread = new UpdateProjectCurrentAmountAfterDeletingDonationThread(donation);
                    Thread thread2 = new Thread(updateAmountThread);
                    thread2.start();
                    waitThread(thread2);
                    UpdateDonationAmountThread updateDonationAmountThread = new UpdateDonationAmountThread(donation.getDonationId(), newAmount);
                    Thread thread3 = new Thread(updateDonationAmountThread);
                    thread3.start();
                    waitThread(thread3);
                    UpdateDonationFundingProjectThread updateDonationFundingProjectThread = new UpdateDonationFundingProjectThread(donation.getDonationId(), optionalProject.get().getProjectId());
                    Thread thread4 = new Thread(updateDonationFundingProjectThread);
                    thread4.start();
                    waitThread(thread4);
                    GetDonationByIdThread getDonationByIdThread = new GetDonationByIdThread(donation.donationId);
                    Thread thread5 = new Thread(getDonationByIdThread);
                    thread5.start();
                    waitThread(thread5);
                    Donation updatedDonation = getDonationByIdThread.getDonation();
                    AddDonationToProjectThread addDonationToProjectThread = new AddDonationToProjectThread(updatedDonation,optionalProject.get().getProjectId());
                    Thread thread6 = new Thread(addDonationToProjectThread);
                    thread6.start();
                    waitThread(thread6);

                    noteTheChange("Editing a donation amount.",String.valueOf(donation.getDonationAmount()),newAmountString);
                    noteTheChange("Editing a donation funded project.",oldProject.getProjectName(),newProjectName);

                }else{
                    if(!isDouble(newAmountString)){
                        showErrorInfoDialog("You entered the wrong amount in the current field");//
                    }else{
                        showErrorInfoDialog("Unknown project name!");
                    }
                    return;
                }
            }else {
                Project oldProject = Database.getProjectById(donation.whatIsFundedProjectId());
                if (projectNameCheckBox.isSelected()) {
                    String newProjectName = newProjectNameTextField.getText();
                    GetProjectByNameThread getProjectByNameThread = new GetProjectByNameThread(newProjectName);
                    Thread thread = new Thread(getProjectByNameThread);
                    thread.start();
                    waitThread(thread);
                    Optional<Project> optionalProject = getProjectByNameThread.getProjectOptional();

                    if (optionalProject.isPresent()) {
                        UpdateProjectCurrentAmountAfterDeletingDonationThread updateAmountThread = new UpdateProjectCurrentAmountAfterDeletingDonationThread(donation);
                        Thread thread1 = new Thread(updateAmountThread);
                        thread1.start();
                        waitThread(thread1);

                        UpdateDonationFundingProjectThread updateDonationFundingProjectThread = new UpdateDonationFundingProjectThread(donation.getDonationId(), optionalProject.get().getProjectId());
                        Thread thread2 = new Thread(updateDonationFundingProjectThread);
                        thread2.start();
                        waitThread(thread2);
                        AddDonationToProjectThread addDonationToProjectThread = new AddDonationToProjectThread(donation,optionalProject.get().getProjectId());
                        Thread thread3 = new Thread(addDonationToProjectThread);
                        thread3.start();
                        waitThread(thread3);
                        noteTheChange("Editing a donation funded project.",oldProject.getProjectName(),newProjectName);
                    } else {
                        showErrorInfoDialog("Unknown project name!");
                        return;
                    }

                }
                if (amountCheckBox.isSelected()) {
                    String newAmountString = newProjectAmountTextField.getText();
                    if (!isDouble(newAmountString)) {
                        showErrorInfoDialog("You entered the wrong amount in the current field");
                        return;
                    }
                    double newAmount = Double.parseDouble(newAmountString);

                    UpdateDonationAmountThread updateDonationAmountThread = new UpdateDonationAmountThread(donation.getDonationId(), newAmount);
                    Thread thread = new Thread(updateDonationAmountThread);
                    thread.start();
                    waitThread(thread);
                    UpdateProjectCurrentAmountAfterEditingDonationThread editAmount = new UpdateProjectCurrentAmountAfterEditingDonationThread(donation,newAmount);
                    Thread thread1 = new Thread(editAmount);
                    thread1.start();
                    waitThread(thread1);
                    noteTheChange("Editing a donation amount.",String.valueOf(donation.getDonationAmount()),newAmountString);

                }
            }
            updateData();
            showSuccessInfoDialog("Donation successfully updated!");
        }

    }
    private void showSuccessInfoDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showErrorInfoDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private boolean showConfirmationDialog( String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Please confirm");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
    public void noteTheChange(String typeOfChange,String before,String after){
        NoteTheChange<String> change = new NoteTheChange<>("Organization",typeOfChange,before,after);
        Utils.saveChanges(change);
    }
    public void waitThread(Thread thread){
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
