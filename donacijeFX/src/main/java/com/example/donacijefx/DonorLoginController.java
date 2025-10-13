package com.example.donacijefx;

import Exceptions.UserIsNotRegisteredException;
import Exceptions.WrongPasswordException;
import domain.Donor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Utils;

import java.io.IOException;

public class DonorLoginController {
    @FXML
    private TextField userNameTextField;
    @FXML
    private TextField passwordTextField;
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

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
    public void showDonorRegisterScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("donorregister.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            HelloApplication.getmainStage().setTitle("Register");
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

    public void loadUser(){
        boolean doesItExist = false;

        String userName = userNameTextField.getText();
        String password = passwordTextField.getText();

        doesItExist = Utils.isDonorRegistered(userName);
        if(userName.isEmpty()||password.isEmpty()){
            showInfoDialog1("You must fill in all fields!" );
            return;
        }
        try{
            Utils.isDonorRegistered_Login(userName);
        }catch (UserIsNotRegisteredException ex){
            logger.error(ex.getMessage());
            showInfoDialog1("Username" + " \"" + userName + "\" " + " is not yet Registered! Please register with the button below." );
            return;
        }

        try{
            boolean boolValue = Utils.checkDonorsPassword(userName,password);

            Donor donor = Utils.getDonor(userName,password);
            Utils.saveDonorToActiveUserFile(donor);
            showDonorUserInterface(donor.getDonorUserName());

        }catch (WrongPasswordException ex){
            showInfoDialog2(ex.getMessage());
            logger.error(ex.getMessage());
            return;
        }

    }

    private void showInfoDialog1(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("You need to register first!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showInfoDialog2(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Wrong password!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
