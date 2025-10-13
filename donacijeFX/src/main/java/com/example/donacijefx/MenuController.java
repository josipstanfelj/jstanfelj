package com.example.donacijefx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import utils.Utils;

import java.io.IOException;

public class MenuController {
    public void showMyAccountInformation(){

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("donorUserInfo.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load(), 850, 578);
            HelloApplication.getmainStage().setTitle("");
            HelloApplication.getmainStage().setScene(scene);
            HelloApplication.getmainStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void showMakeNewDonationScreen(){

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("createdonation.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load(), 850, 578);
            HelloApplication.getmainStage().setTitle("Create Donation");
            HelloApplication.getmainStage().setScene(scene);
            HelloApplication.getmainStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void showAllDonationsScreen(){

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("alldonations.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load(), 850, 578);
            HelloApplication.getmainStage().setTitle("All Donations");
            HelloApplication.getmainStage().setScene(scene);
            HelloApplication.getmainStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void showMyDonationsScreen(){

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mydonations.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load(), 850, 578);
            HelloApplication.getmainStage().setTitle("My Donations");
            HelloApplication.getmainStage().setScene(scene);
            HelloApplication.getmainStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void showDeleteDonationScreen(){

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("deletedonation.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load(), 850, 578);
            HelloApplication.getmainStage().setTitle("Delete donation");
            HelloApplication.getmainStage().setScene(scene);
            HelloApplication.getmainStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void showAllProjectsScreen(){

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("allprojects.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load(), 850, 578);
            HelloApplication.getmainStage().setTitle("All projects");
            HelloApplication.getmainStage().setScene(scene);
            HelloApplication.getmainStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void showEditDonationScreen(){

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("editdonation.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load(), 850, 578);
            HelloApplication.getmainStage().setTitle("Edit donation");
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
            HelloApplication.getmainStage().setTitle("Home screen");
            HelloApplication.getmainStage().setScene(scene);
            HelloApplication.getmainStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void logOut(){
        Utils.clearActiveDonorUserFile();
        showHomeScreen();
    }

}
