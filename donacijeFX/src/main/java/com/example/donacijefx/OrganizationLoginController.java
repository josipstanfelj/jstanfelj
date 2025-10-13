package com.example.donacijefx;

import Exceptions.UserIsNotRegisteredException;
import Exceptions.WrongPasswordException;
import domain.Organization;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Utils;

import java.io.IOException;

public class OrganizationLoginController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @FXML
    private TextField userNameTextField;
    @FXML
    private TextField passwordTextField;

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
    public void showOrganizationRegisterScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("organizationregister.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            HelloApplication.getmainStage().setTitle("Register");
            HelloApplication.getmainStage().setScene(scene);
            HelloApplication.getmainStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void showOrganizationUserInterface(String user){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("organizationuserinterface.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load(), 850, 578);
            HelloApplication.getmainStage().setTitle("Organization User - " +user);
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


        if(userName.isEmpty()||password.isEmpty()){
            showInfoDialog1("You must fill in all fields!" );
            return;
        }
        try{
            Utils.isOrganizationRegistered_Login(userName);
        }catch (UserIsNotRegisteredException ex){
            logger.error(ex.getMessage());
            showInfoDialog1("Username" + " \"" + userName + "\" " + " is not yet Registered! Please register with the button below." );
            return;
        }

        try{
            boolean boolValue = Utils.checkOrganizationUserPassword(userName,password);

            Organization organization = Utils.getOrganization(userName,password);
            Utils.saveOrganizationToActiveUserFile(organization);
            showOrganizationUserInterface(organization.getUserName());

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
