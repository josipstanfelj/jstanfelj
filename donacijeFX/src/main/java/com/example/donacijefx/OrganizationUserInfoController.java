package com.example.donacijefx;

import domain.Organization;
import domain.OrganizationInfo;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Utils;

public class OrganizationUserInfoController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @FXML
    private Label einLabel;
    @FXML
    private Label founderNameLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label stateLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label organizationTypeLabel;
    @FXML
    private Label instagramLabel;
    @FXML
    private Label establishmentDateLabel;


    public void initialize(){
        Organization activeOrganization = Utils.getActiveOrganizationUser();
        String EIN = String.valueOf(activeOrganization.getEmployerIdentificationNumber().EIN());
        einLabel.setText(EIN);
        founderNameLabel.setText(activeOrganization.getEstablishmentInfo().getFounderName());
        OrganizationInfo newOrganizationInfo = activeOrganization.getOrganizationInfo();
        cityLabel.setText(newOrganizationInfo.getCity());
        streetLabel.setText(newOrganizationInfo.getStreet());
        Integer postalCode = newOrganizationInfo.getPostalCode();
        postalCodeLabel.setText(postalCode.toString());
        stateLabel.setText(newOrganizationInfo.getState());
        emailLabel.setText(newOrganizationInfo.getEmail());
        organizationTypeLabel.setText(activeOrganization.getEstablishmentInfo().getIndustry());
        instagramLabel.setText(activeOrganization.getSocialMediaLinks().getInstagram());
        establishmentDateLabel.setText(activeOrganization.getEstablishmentInfo().getFoundationDate().toString());
    }
}
