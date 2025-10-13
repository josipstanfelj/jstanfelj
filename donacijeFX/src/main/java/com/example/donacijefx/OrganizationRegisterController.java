package com.example.donacijefx;

import Exceptions.UserIsRegisteredException;
import domain.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import threads.SaveOrganizationThread;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrganizationRegisterController {
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
    public void showOrganizationLoginScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("organizationlogin.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            HelloApplication.getmainStage().setTitle("Register");
            HelloApplication.getmainStage().setScene(scene);
            HelloApplication.getmainStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private TextField userNameTextField;
    @FXML
    private TextField passwordTextField;

    @FXML
    private Button goBackButton;



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


    public void saveNewOrganizationUser(){
        List<Organization> organizationList = new ArrayList<>();
        organizationList = Utils.getOrganizationUsersFromFile();
        boolean doesItExist = false;

        if(userNameTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()){
            showInfoDialog("You must fill in all fields!");
            return;
        }

        String userName = userNameTextField.getText();
        String password = passwordTextField.getText();

        String donorName = null;

        EmployerIdentificationNumber employerIdentificationNumber = null;
        OrganizationInfo organizationInfo = null;
        SocialMediaLinks socialMediaLinks = null;
        EstablishmentInfo establishmentInfo = null;

        try{
            Utils.isOrganizationRegistered_Register(userName);
            OrganizationAdditionalInformation organizationAdditionalInformation = null;

            organizationAdditionalInformation = OrganizationAdditionalInformation.openAndGetAdditionalInformationScreen();
            if (!organizationAdditionalInformation.isEmpty()) {
                employerIdentificationNumber = organizationAdditionalInformation.employerIdentificationNumber;
                organizationInfo = organizationAdditionalInformation.organizationInfo;
                socialMediaLinks = organizationAdditionalInformation.socialMediaLinks;
                establishmentInfo = organizationAdditionalInformation.establishmentInfo;
            }else{
                //
                return;
            }

        }catch (UserIsRegisteredException ex){
            showInfoDialog("Username" + " \"" + userName + "\" " + " is already taken! Please choose another username." );
            logger.info(ex.getMessage());
            return;
        }

        int id = Utils.getNextOrganizaitonUserId();
        String passwordHashCode = String.valueOf(password.hashCode());
        Organization organization = new Organization(userName,passwordHashCode,employerIdentificationNumber,organizationInfo,socialMediaLinks,establishmentInfo,id);
        Utils.saveOrganizationToActiveUserFile(organization);
        organizationList.add(organization);
        Utils.saveOrganizationUser(organizationList);

        SaveOrganizationThread saveOrganizationThread = new SaveOrganizationThread(organization);
        Thread thread = new Thread(saveOrganizationThread);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        showOrganizationUserInterface(organization.getUserName());
    }

    private void showInfoDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User already exists!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
