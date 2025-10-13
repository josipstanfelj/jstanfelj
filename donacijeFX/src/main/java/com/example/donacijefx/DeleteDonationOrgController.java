package com.example.donacijefx;

import Exceptions.MissingSelectionException;
import database.Database;
import domain.Donation;
import domain.NoteTheChange;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import threads.DeleteDonationThread;
import threads.GetAllDonationsThread;
import threads.UpdateProjectCurrentAmountAfterDeletingDonationThread;
import utils.Utils;
import java.util.Optional;


public class DeleteDonationOrgController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @FXML
    private ListView<Donation> itemListView;


    public void initialize(){
        GetAllDonationsThread getAllDonationsThread = new GetAllDonationsThread();
        Thread thread = new Thread(getAllDonationsThread);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ObservableList<Donation> donationList = FXCollections.observableArrayList(getAllDonationsThread.donationList);
        itemListView.setItems(donationList);
        itemListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        itemListView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<Donation>) change -> {
            System.out.println("Selektirane stavke: " + itemListView.getSelectionModel().getSelectedItems());
        });

    }
    public void updateData(){
        GetAllDonationsThread getAllDonationsThread = new GetAllDonationsThread();
        Thread thread = new Thread(getAllDonationsThread);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ObservableList<Donation> donationList = FXCollections.observableArrayList(Database.getAllDonations());
        itemListView.setItems(donationList);
    }

    public void deleteDonation(){
        try{
            if(itemListView.getSelectionModel().isEmpty()){
                throw new MissingSelectionException("You have to choose which donation you want to edit!");
            }
        }catch (MissingSelectionException ex){
            showInfoDialog(ex.getMessage());
            logger.info(ex.getMessage());
            return;
        }
        if(showConfirmationDialog("Are you sure you want make this changes?")){
            Donation donation = itemListView.getSelectionModel().getSelectedItem();

            noteTheChange("Donation amount : "+ donation.getDonationAmount());

            UpdateProjectCurrentAmountAfterDeletingDonationThread updateAmount = new UpdateProjectCurrentAmountAfterDeletingDonationThread(donation);
            Thread thread = new Thread(updateAmount);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            DeleteDonationThread deleteDonationThread = new DeleteDonationThread(donation.donationId);
            Thread thread1 = new Thread(deleteDonationThread);
            thread1.start();
            try {
                thread1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            updateData();
            showInfoDialog("Donation successfully canceled!");
        }

    }
    private void showInfoDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
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
    public void noteTheChange(String name){
        String typeOfChange = "Deleting a donation.";
        String none = "None";
        NoteTheChange<String> change = new NoteTheChange<>("Organization",typeOfChange,name,none);
        Utils.saveChanges(change);
    }
}
