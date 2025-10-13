package com.example.donacijefx;

import domain.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import threads.GetAllSuspendedUsersThread;
import threads.RemoveUserFromSuspensionListThread;
import threads.SaveSuspendedUserInfoThread;
import utils.Utils;
import java.util.*;
import java.util.stream.Collectors;

public class SuspendTheUserController {
    @FXML
    private ListView<Donor> userListView;
    @FXML
    private TextArea reasonTextArea;
    @FXML
    private TableView<Donor> suspendedUsersTableView;
    @FXML
    private TableColumn<Donor,String> suspendedUserTableColumn;


    public void initialize(){
        List<Donor> donorList = Utils.getDonorUsersFromFile();
        ObservableList<Donor> donorObservableList = FXCollections.observableArrayList(donorList);
        userListView.setItems(donorObservableList);
        userListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        GetAllSuspendedUsersThread getAllSuspendedUsersThread = new GetAllSuspendedUsersThread();
        Thread thread = new Thread(getAllSuspendedUsersThread);
        thread.start();
        waitThread(thread);
        SuspendedUsers allSuspendedUsers = getAllSuspendedUsersThread.getSuspendedUsers();
        List<Donor> suspendedUsers = new ArrayList<>();

        for(Integer index : allSuspendedUsers.suspendedUsersMap.keySet()){
            suspendedUsers.add(Utils.getDonorById(index));
        }

        Set<Donor> suspendedUsersSet = new TreeSet<>((c1,c2)-> c1.getUserName().compareTo(c2.getUserName()));
        suspendedUsersSet.addAll(suspendedUsers);
        List<Donor> suspendedUsersList = suspendedUsersSet.stream().collect(Collectors.toList());



        suspendedUserTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Donor, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Donor, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getUserName());
            }
        });

        ObservableList<Donor> suspendedUsersObservableList = FXCollections.observableList(suspendedUsersList);
        suspendedUsersTableView.setItems(suspendedUsersObservableList);

    }

    public void suspendUser(){
        if(showConfirmationDialog("Are you sure you want suspend this user?")){
            if(reasonTextArea.getText().isEmpty() || userListView.getSelectionModel().isEmpty()){
                showInfoDialog("It is necessary to select the desired donor and fill in the reason field!");
                return;
            }
            String reason = reasonTextArea.getText();
            Donor donor = userListView.getSelectionModel().getSelectedItem();

            Organization organization = Utils.getActiveOrganizationUser();
            SaveSuspendedUserInfoThread saveSuspendedUserInfoThread = new SaveSuspendedUserInfoThread(donor,organization,reason);
            Thread thread = new Thread(saveSuspendedUserInfoThread);
            thread.start();
            waitThread(thread);
            noteTheChange("Active user","Suspended user: " + donor.getDonorName());
            initialize();
        }
        showInfoDialog("You successfully suspended the user!");
    }

    public void removeFromSuspensionList(){
        if(userListView.getSelectionModel().isEmpty()){
            showInfoDialog("It is necessary to select the desired donor!");
            return;
        }

        RemoveUserFromSuspensionListThread removeUserFromSuspensionListThread = new RemoveUserFromSuspensionListThread(userListView.getSelectionModel().getSelectedItem());
        Thread thread = new Thread(removeUserFromSuspensionListThread);
        thread.start();
        waitThread(thread);
        Donor donor = userListView.getSelectionModel().getSelectedItem();
        showInfoDialog("You successfully removed User from suspension list!");
        noteTheChange2("Suspended user: ", "Active user: "+ donor.getDonorName());
        initialize();
    }
    private boolean showConfirmationDialog( String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Please confirm");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
    private void showInfoDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void noteTheChange(String value,String value2){
        String typeOfChange = "User suspension.";
        NoteTheChange<String> change = new NoteTheChange<>("Organization",typeOfChange,value,value2);
        Utils.saveChanges(change);
    }
    public void noteTheChange2(String value,String value2){
        String typeOfChange = "User activation. .";
        NoteTheChange<String> change = new NoteTheChange<>("Organization",typeOfChange,value,value2);
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
