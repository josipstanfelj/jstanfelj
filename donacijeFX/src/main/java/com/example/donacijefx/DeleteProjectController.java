package com.example.donacijefx;

import Exceptions.MissingSelectionException;
import domain.Donation;
import domain.NoteTheChange;
import domain.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import threads.*;
import utils.Utils;
import java.util.List;
import java.util.Optional;


public class DeleteProjectController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @FXML
    private ListView<Project> itemListView;


    public void initialize(){
        GetAllProjectsThread getAllProjectsThread = new GetAllProjectsThread();
        Thread thread = new Thread(getAllProjectsThread);
        thread.start();
        waitThread(thread);

        ObservableList<Project> projectList = FXCollections.observableArrayList(getAllProjectsThread.projectList);
        itemListView.setItems(projectList);
        itemListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


    }
    public void updateData(){
        GetAllProjectsThread getAllProjectsThread = new GetAllProjectsThread();
        Thread thread = new Thread(getAllProjectsThread);
        thread.start();
        waitThread(thread);
        ObservableList<Project> projectList = FXCollections.observableArrayList(getAllProjectsThread.projectList);
        itemListView.setItems(projectList);
    }

    public void deleteProject(){
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
            Project project = itemListView.getSelectionModel().getSelectedItem();
            int projectId = project.getProjectId();
            GetDonationsByFundingProjectIdThread getDonationsByFundingProjectIdThread = new GetDonationsByFundingProjectIdThread(projectId);
            Thread thread = new Thread(getDonationsByFundingProjectIdThread);
            thread.start();
            waitThread(thread);

            List<Donation> donationList = getDonationsByFundingProjectIdThread.donationList;
            for(Donation donation : donationList){
                DeleteDonationThread deleteDonationThread = new DeleteDonationThread(donation.getDonationId());
                Thread thread1 = new Thread(deleteDonationThread);
                thread1.start();
                waitThread(thread1);
            }

            DeleteProjectThread deleteProjectThread = new DeleteProjectThread(projectId);
            Thread thread2 = new Thread(deleteProjectThread);
            thread2.start();
            waitThread(thread2);

            updateData();
            showInfoDialog("You have successfully deleted the selected project!");
            noteTheChange(project.getProjectName());

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
    public void noteTheChange(String valueBefore){
        String typeOfChange = "Deleting a project.";
        String none = "None";
        NoteTheChange<String> change = new NoteTheChange<>("Organization",typeOfChange,valueBefore,none);
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
