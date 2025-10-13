package com.example.donacijefx;

import domain.Donation;
import domain.Project;
import domain.ProjectStatusAnalyzer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import threads.GetAllProjectsThread;
import threads.GetDonationsByFundingProjectIdThread;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class ProjectStatusController implements Initializable {
    @FXML
    private PieChart pieChart;
    @FXML
    private ListView<Project> projectListView;
    @FXML
    private ListView<Donation> donationsListView;

    @FXML
    private Label notifycationLabel;
    @FXML
    private Label administrativeCostscationLabel;
    @FXML
    private Label governmentTaxscationLabel;
    @FXML
    private Label transactionCostscationLabel;


    public void initialize(URL location, ResourceBundle resources) {
        GetAllProjectsThread getAllProjectsThread = new GetAllProjectsThread();
        Thread thread = new Thread(getAllProjectsThread);
        thread.start();
        waitThread(thread);
        ObservableList<Project> projectList = FXCollections.observableArrayList(getAllProjectsThread.getProjectList());
        projectListView.setItems(projectList);
        projectListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        PieChart.Data slice1 = new PieChart.Data("Collected funds", 0);
        PieChart.Data slice2 = new PieChart.Data("Goal", 100);
        pieChart.getData().addAll(slice1, slice2);

    }


    public void showProjectStatus(){
        pieChart.getData().clear();
        if(projectListView.getSelectionModel().isEmpty()){
            showInfoDialog("You have to choose project first!");
            return;
        }
        Project project = projectListView.getSelectionModel().getSelectedItem();
        double transactionCosts = 0;
        double governmentTax = 0;
        double administrativeCosts = 0;

        for(Donation donation : project.getDonations()){
            transactionCosts = transactionCosts + donation.getTransactionCosts();
            governmentTax = governmentTax + donation.getGovernmentTax();
            administrativeCosts = administrativeCosts + donation.getAdministrativeCosts();
        }

        administrativeCostscationLabel.setText("Administrative costs =  " + Math.round(administrativeCosts*100)/(double)100);
        governmentTaxscationLabel.setText("Government tax =  " + Math.round(governmentTax*100)/(double)100);
        transactionCostscationLabel.setText("Transaction costs =  " + Math.round(transactionCosts*100)/(double)100);

        Double currentAmount = project.getCurrentAmount();
        Double targetAmount = project.getTargetAmount();

        ProjectStatusAnalyzer<Double,Double> projectStatusAnalyzer = new ProjectStatusAnalyzer<>();
        projectStatusAnalyzer.analyze(currentAmount,targetAmount,notifycationLabel);

        PieChart.Data slice1 = new PieChart.Data("Collected funds", projectStatusAnalyzer.getCollectedFunds());
        PieChart.Data slice2 = new PieChart.Data("Goal", projectStatusAnalyzer.getMissingFunds());

        pieChart.getData().addAll(slice1, slice2);

        GetDonationsByFundingProjectIdThread getDonationsByFundingProjectIdThread = new GetDonationsByFundingProjectIdThread(project.getProjectId());
        Thread thread = new Thread(getDonationsByFundingProjectIdThread);
        thread.start();
        waitThread(thread);
        List<Donation> donationList = getDonationsByFundingProjectIdThread.getDonationList();

        ObservableList<Donation> donationObservableList = FXCollections.observableArrayList(donationList);
        donationsListView.setItems(donationObservableList);

    }

    private void showInfoDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void waitThread(Thread thread){
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
