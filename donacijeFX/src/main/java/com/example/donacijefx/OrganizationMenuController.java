package com.example.donacijefx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import utils.Utils;

import java.io.IOException;

public class OrganizationMenuController {
    public void showMyAccountInformation2(){

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("organizationuserinfo.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load(), 850, 578);
            HelloApplication.getmainStage().setTitle("User Info");
            HelloApplication.getmainStage().setScene(scene);
            HelloApplication.getmainStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void showAllProjectsScreen(){

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("allprojects2.fxml"));

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

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("editdonation-org.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load(), 850, 578);
            HelloApplication.getmainStage().setTitle("Edit donation");
            HelloApplication.getmainStage().setScene(scene);
            HelloApplication.getmainStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void showDeleteDonationScreen(){

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("deletedonation-org.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load(), 850, 578);
            HelloApplication.getmainStage().setTitle("Delete donation");
            HelloApplication.getmainStage().setScene(scene);
            HelloApplication.getmainStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void showCreateNewProjectScreen(){

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("createproject.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load(), 850, 578);
            HelloApplication.getmainStage().setTitle("Create project");
            HelloApplication.getmainStage().setScene(scene);
            HelloApplication.getmainStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void showDeleteProjectScreen(){

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("deleteproject.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load(), 850, 578);
            HelloApplication.getmainStage().setTitle("Delete project");
            HelloApplication.getmainStage().setScene(scene);
            HelloApplication.getmainStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void showEditProjectScreen(){

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("editproject.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load(), 850, 578);
            HelloApplication.getmainStage().setTitle("Edit project");
            HelloApplication.getmainStage().setScene(scene);
            HelloApplication.getmainStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void showProjectStatusScreen(){

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("projectstatus.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load(), 850, 578);
            HelloApplication.getmainStage().setTitle("Project Status");
            HelloApplication.getmainStage().setScene(scene);
            HelloApplication.getmainStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void showChangeLogScreen(){

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("changelog.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load(), 850, 578);
            HelloApplication.getmainStage().setTitle("Change log");
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
        Utils.clearActiveOrganizationUserFile();
        showHomeScreen();
    }

    public void showSuspendDonatorScreen(){

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("suspedtheuser.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load(), 850, 578);
            HelloApplication.getmainStage().setTitle("Suspend The User");
            HelloApplication.getmainStage().setScene(scene);
            HelloApplication.getmainStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
