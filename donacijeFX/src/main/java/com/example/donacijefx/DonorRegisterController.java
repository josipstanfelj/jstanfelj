package com.example.donacijefx;

import Exceptions.UserIsRegisteredException;
import domain.Donor;
import domain.DonorInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import threads.SaveDonorThread;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DonorRegisterController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @FXML
    private TextField userNameTextField;
    @FXML
    private TextField passwordTextField;

    public void showOrganizationLoginScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            HelloApplication.getmainStage().setTitle("Login");
            HelloApplication.getmainStage().setScene(scene);
            HelloApplication.getmainStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showDonorUserInterface(String user){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("donoruserinterface.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load(), 850, 578);
            HelloApplication.getmainStage().setTitle("Donor User - "+user);
            HelloApplication.getmainStage().setScene(scene);
            HelloApplication.getmainStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void showDonorLoginScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("donorlogin.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            HelloApplication.getmainStage().setTitle("Register");
            HelloApplication.getmainStage().setScene(scene);
            HelloApplication.getmainStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void showHomeScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            HelloApplication.getmainStage().setTitle("Home");
            HelloApplication.getmainStage().setScene(scene);
            HelloApplication.getmainStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveNewDonorUser(){
        List<Donor> donorList = new ArrayList<>();
        donorList = Utils.getDonorUsersFromFile();
        boolean doesItExist = false;

        if(userNameTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()){
            showInfoDialog("You must fill in all fields!");
            return;
        }

        String userName = userNameTextField.getText();
        String password = passwordTextField.getText();
        doesItExist = Utils.isDonorRegistered(userName);
        String donorName = null;
        String donorLastName = null;
        DonorInfo donorInfo = null;

        try{
            Utils.isDonorRegistered_Register(userName);
            DonorAdditionalInformation donorAdditionalInformation = DonorAdditionalInformation.openAndGetAdditionalInformationScreen();

            if (!donorAdditionalInformation.isEmpty()) {
                donorInfo = donorAdditionalInformation.getDonorInfo();
                donorName = donorAdditionalInformation.getDonorName();
                donorLastName = donorAdditionalInformation.getDonorLastName();
            }else{
                return;
            }

        }catch (UserIsRegisteredException ex){
            showInfoDialog("Username" + " \"" + userName + "\" " + " is already taken! Please choose another username." );
            logger.info(ex.getMessage());
            return;
        }

        int id = Utils.getNextDonorUserId();
        String passwordHashCode = String.valueOf(password.hashCode());
        Donor donor = new Donor(userName,passwordHashCode,donorName,donorLastName,donorInfo,id);
        Utils.saveDonorToActiveUserFile(donor);
        donorList.add(donor);
        Utils.saveDonorUser(donorList);
        SaveDonorThread saveDonorThread = new SaveDonorThread(donor);
        Thread thread = new Thread(saveDonorThread);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        showDonorUserInterface(donor.getDonorUserName());
    }

    private void showInfoDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User already exists!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
