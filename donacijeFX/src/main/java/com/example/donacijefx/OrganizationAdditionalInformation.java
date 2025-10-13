package com.example.donacijefx;

import domain.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class OrganizationAdditionalInformation {

    private static Stage additionalInformationStage;

    public static void setAdditionalInformationStage(Stage stage) {
        additionalInformationStage = stage;
    }

    public static Stage getAdditionalInformationStage() {
        return additionalInformationStage;
    }
    public static OrganizationAdditionalInformation openAndGetAdditionalInformationScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("organizationadditionalinformation.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);

            OrganizationAdditionalInformation.setAdditionalInformationStage(new Stage());
            OrganizationAdditionalInformation.getAdditionalInformationStage().setTitle("New User - Organization Additional Information");
            OrganizationAdditionalInformation.getAdditionalInformationStage().setScene(scene);
            OrganizationAdditionalInformation.getAdditionalInformationStage().initModality(Modality.APPLICATION_MODAL);
            OrganizationAdditionalInformation.getAdditionalInformationStage().showAndWait();

            return fxmlLoader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private TextField einTextField;
    @FXML
    private TextField stateTextField;
    @FXML
    private TextField cityTextFiled;
    @FXML
    private TextField streetTextField;
    @FXML
    private TextField postalCodeTextField;
    @FXML
    private TextField emailTextFiled;
    @FXML
    private TextField facebookTextField;
    @FXML
    private TextField instagramTextField;
    @FXML
    private TextField twitterTextField;
    @FXML
    private TextField founderNameTextField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField industryTextField;
    @FXML
    private Button goBackButton;

    public EmployerIdentificationNumber employerIdentificationNumber;
    public OrganizationInfo organizationInfo;
    public SocialMediaLinks socialMediaLinks;
    public EstablishmentInfo establishmentInfo;

    public boolean isEmpty(){
        boolean bool = false;
        if(employerIdentificationNumber == null || organizationInfo == null || socialMediaLinks == null || establishmentInfo ==null){
            bool = true;
        }
        return bool;
    }

    private boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean checkIfDatePickerIsEmpty(DatePicker datePicker){
        boolean bool = true;
        if (datePicker.getValue() != null) {
            bool = false;
        }
        return bool;
    }

    @FXML
    public void goBack(){
        additionalInformationStage.close();

    }

    @FXML
    public void saveAdditionalInformation(){
        int EIN = 0;

        String state = stateTextField.getText();
        String city =  cityTextFiled.getText();
        String street = streetTextField.getText();
        int postalCode = 0;
        String email = emailTextFiled.getText();
        String facebook = facebookTextField.getText();
        String instagram = instagramTextField.getText();
        String twitter = twitterTextField.getText();
        String founderName = founderNameTextField.getText();
        String industry = industryTextField.getText();
        LocalDate localDate = null;

        if (isInteger(postalCodeTextField.getText()) && isInteger(einTextField.getText())) {
            EIN = Integer.parseInt(einTextField.getText());
            postalCode = Integer.parseInt(postalCodeTextField.getText());
        } else {
            showErrorDialog("You entered wrong postalCode, houseNumber or phone number!");
            return;
        }
        if(state.isEmpty()||city.isEmpty()||street.isEmpty()|| EIN == 0||postalCode == 0||email.isEmpty()||facebook.isEmpty()||twitter.isEmpty()||founderName.isEmpty()||industry.isEmpty()){
            showErrorDialog("You must fill in all fields!");
            return;
        }
        if(checkIfDatePickerIsEmpty(datePicker)){
            showErrorDialog("You must fill in the date field!");
            return;
        }else{
            localDate = datePicker.getValue();
        }
        employerIdentificationNumber = new EmployerIdentificationNumber(EIN);
        organizationInfo = new OrganizationInfo(city,street,postalCode,state,email);
        socialMediaLinks = new SocialMediaLinks(facebook,twitter,instagram);
        establishmentInfo = new EstablishmentInfo(founderName,localDate,industry);
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
