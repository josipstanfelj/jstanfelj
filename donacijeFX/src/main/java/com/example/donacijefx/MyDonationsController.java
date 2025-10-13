package com.example.donacijefx;

import database.Database;
import domain.Donation;
import domain.Donor;
import domain.Project;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import threads.GetDonationsByDonorIdThread;
import utils.Utils;

import java.util.List;


public class MyDonationsController {

    @FXML
    private TableView<Donation> donationTableView;
    @FXML
    private TableColumn<Donation,Double> amountTableColumn;
    @FXML
    private TableColumn<Donation,String> dateTableColumn;
    @FXML
    private TableColumn<Donation,String> donorNameTableColumn;
    @FXML
    private TableColumn<Donation,String> fundingProjectNameTableColumn;

    public void initialize(){

        GetDonationsByDonorIdThread getDonationsByDonorIdThread = new GetDonationsByDonorIdThread(Utils.getActiveDonorUserId());
        Thread thread = new Thread(getDonationsByDonorIdThread);
        thread.start();
        waitThread(thread);
        List<Donation> allDonations = getDonationsByDonorIdThread.getDonationList();


        amountTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Donation, Double>, ObservableValue<Double>>() {
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Donation, Double> param) {
                return new ReadOnlyObjectWrapper<>(param.getValue().getDonationAmount());
            }
        });

        dateTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Donation,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Donation,String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getDonationDateAndTime());
            }
        });
        donorNameTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Donation, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Donation,String> param) {
                Donor donor = Utils.getDonorById(param.getValue().whatIsDonorId());
                return new ReadOnlyStringWrapper(donor.getDonorName());
            }
        });
        fundingProjectNameTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Donation,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Donation,String> param) {
                Project project = Database.getProjectById(param.getValue().whatIsFundedProjectId());
                return new ReadOnlyStringWrapper(project.getProjectName());
            }
        });

        ObservableList<Donation> donationsObservableList = FXCollections.observableList(allDonations);
        donationTableView.setItems(donationsObservableList);

    }
    public void waitThread(Thread thread){
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
