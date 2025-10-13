package com.example.donacijefx;

import domain.Donor;
import domain.DonorInfo;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import utils.Utils;

public class DonorUserInfoController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label houseNumberLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label phoneNumberLabel;


    public void initialize(){
       Donor activeDonor = Utils.getActiveDonorUser();
       nameLabel.setText(activeDonor.getDonorName());
       lastNameLabel.setText(activeDonor.getDonorLastname());
       DonorInfo newDonorInfo = activeDonor.getInfo();
       cityLabel.setText(newDonorInfo.getCity());
       streetLabel.setText(newDonorInfo.getStreet());
       Integer postalCode = newDonorInfo.getPostalCode();
       postalCodeLabel.setText(postalCode.toString());
       Integer houseNumber = newDonorInfo.getHouseNumber();
       houseNumberLabel.setText(houseNumber.toString());
       emailLabel.setText(newDonorInfo.getEmail());
       Integer phoneNumber = newDonorInfo.getPhoneNumber();
       phoneNumberLabel.setText(phoneNumber.toString());
    }

}
