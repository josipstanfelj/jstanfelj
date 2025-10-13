package com.example.donacijefx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
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

}