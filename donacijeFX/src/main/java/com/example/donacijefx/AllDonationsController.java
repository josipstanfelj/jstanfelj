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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import threads.GetAllDonationsThread;
import utils.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class AllDonationsController {
    @FXML
    private TextField donorNameTextField;
    @FXML
    private ComboBox<String> sortTypeComboBox;
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
        GetAllDonationsThread getAllDonationsThread = new GetAllDonationsThread();
        Thread thread = new Thread(getAllDonationsThread);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Donation> allDonations = getAllDonationsThread.getDonationList();
        sortTypeComboBox.getItems().add("Ascending");
        sortTypeComboBox.getItems().add("Descending");
        sortTypeComboBox.setOnAction(event -> {
            String selectedItem = sortTypeComboBox.getSelectionModel().getSelectedItem();
        });

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

    public void donationSearch(){

        GetAllDonationsThread getAllDonationsThread = new GetAllDonationsThread();
        Thread thread = new Thread(getAllDonationsThread);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Donation> allDonations = getAllDonationsThread.getDonationList();
        String donorName = "";
        List<Donation> donationFilteredList = new ArrayList<>();
        if(!donorNameTextField.getText().isEmpty()){
            donorName = donorNameTextField.getText();
            int id = Utils.getDonorId(donorName);

            donationFilteredList = allDonations.stream().filter(donation -> donation.whatIsDonorId() == id).collect(Collectors.toList());

            if(sortTypeComboBox.getSelectionModel().getSelectedItem() != null && sortTypeComboBox.getSelectionModel().getSelectedItem().equals("Ascending")){
                donationFilteredList = donationFilteredList.stream()
                        .sorted((d1, d2) -> Double.compare(d1.getDonationAmount(), d2.getDonationAmount()))
                        .collect(Collectors.toList());
            }else if(sortTypeComboBox.getSelectionModel().getSelectedItem() != null && sortTypeComboBox.getSelectionModel().getSelectedItem().equals("Descending")){
                donationFilteredList = donationFilteredList.stream()
                        .sorted((d1, d2) -> Double.compare(d2.getDonationAmount(), d1.getDonationAmount()))
                        .collect(Collectors.toList());
            }
        }else{
            if(sortTypeComboBox.getSelectionModel().getSelectedItem() != null && sortTypeComboBox.getSelectionModel().getSelectedItem().equals("Ascending")){
                donationFilteredList = allDonations.stream()
                        .sorted((d1, d2) -> Double.compare(d1.getDonationAmount(), d2.getDonationAmount()))
                        .collect(Collectors.toList());

            }else if(sortTypeComboBox.getSelectionModel().getSelectedItem() != null && sortTypeComboBox.getSelectionModel().getSelectedItem().equals("Descending")){
                donationFilteredList = allDonations.stream()
                        .sorted((d1, d2) -> Double.compare(d2.getDonationAmount(), d1.getDonationAmount()))
                        .collect(Collectors.toList());

            }
        }

        ObservableList<Donation> donationsObservableList = FXCollections.observableList(donationFilteredList);
        donationTableView.setItems(donationsObservableList);
    }



}
