package com.example.donacijefx;

import domain.DonorInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class DonorAdditionalInformation {

    private static Stage additionalInformationStage;

    public static void setAdditionalInformationStage(Stage stage) {
        additionalInformationStage = stage;
    }

    public static Stage getAdditionalInformationStage() {
        return additionalInformationStage;
    }
    public static DonorAdditionalInformation openAndGetAdditionalInformationScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("donoradditionalinformation.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);

            DonorAdditionalInformation.setAdditionalInformationStage(new Stage());
            DonorAdditionalInformation.getAdditionalInformationStage().setTitle("New User - Donor Additional Information");
            DonorAdditionalInformation.getAdditionalInformationStage().setScene(scene);
            DonorAdditionalInformation.getAdditionalInformationStage().initModality(Modality.APPLICATION_MODAL);
            DonorAdditionalInformation.getAdditionalInformationStage().showAndWait();

            return fxmlLoader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private TextField nameTextFiled;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField cityTextFiled;
    @FXML
    private TextField streetTextField;
    @FXML
    private TextField postalCodeTextField;
    @FXML
    private TextField houseNumberTextField;
    @FXML
    private TextField emailTextFiled;
    @FXML
    private TextField phoneNumberTextField;

    public String donorName;

    public String donorLastName;

    public DonorInfo donorInfo;

    public boolean isEmpty(){
        boolean bool = false;
        if(donorName == null||donorLastName==null || donorInfo == null){
            bool = true;
        }
        return bool;
    }

    public String getDonorName(){
        return donorName;
    }
    public String getDonorLastName(){
        return donorLastName;
    }
    public DonorInfo getDonorInfo(){
        return donorInfo;
    }
    public boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    @FXML
    public void goBack(){
        additionalInformationStage.close();
    }
    public void saveAdditionalInformation(){
        donorName = nameTextFiled.getText();
        donorLastName = lastNameTextField.getText();

        String city = cityTextFiled.getText();
        String street = streetTextField.getText();

        int postalCode = -1;
        int houseNumber = -1;
        int phoneNumber = -1;
        if (isInteger(postalCodeTextField.getText()) && isInteger(houseNumberTextField.getText()) && isInteger(phoneNumberTextField.getText())) {

            postalCode = Integer.parseInt(postalCodeTextField.getText());
            houseNumber = Integer.parseInt(houseNumberTextField.getText());
            phoneNumber = Integer.parseInt(phoneNumberTextField.getText());

        } else {
            showErrorDialog("You entered wrong postalCode, houseNumber or phone number!");
            return;
        }
        String email = emailTextFiled.getText();
        if(city.isEmpty()||street.isEmpty()||postalCode == 0||email.isEmpty()||houseNumber == -1||postalCode == -1||phoneNumber==-1){
            showErrorDialog("You must fill in all fields!");
        }

        //donorInfo = new DonorInfo(city,street,postalCode,houseNumber,email,phoneNumber);
        donorInfo = new DonorInfo.Builder(city)
                .street(street)
                .postalCode(postalCode)
                .houseNumber(houseNumber)
                .email(email)
                .phoneNumber(phoneNumber)
                .build();

        additionalInformationStage.close();
    }


    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greška");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
