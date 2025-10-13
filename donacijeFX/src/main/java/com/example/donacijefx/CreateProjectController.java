package com.example.donacijefx;

import domain.NoteTheChange;
import domain.Project;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import threads.SaveNewProjectThread;
import utils.Utils;

public class CreateProjectController {
    @FXML
    private TextField projectNameTextField;
    @FXML
    private TextField projectDescriptionTextField;
    @FXML
    private TextField targetAmountTextField;

    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public void initialize(){

    }
    public void saveProject(){
        if(projectNameTextField.getText().isEmpty() || projectDescriptionTextField.getText().isEmpty() || targetAmountTextField.getText().isEmpty()){
            showInfoDialog("You must fill in all fields!");
            return;
        }
        String name = projectNameTextField.getText();
        String description = projectDescriptionTextField.getText();
        String targetAmountString = targetAmountTextField.getText();
        if(!isDouble(targetAmountString)){
            showInfoDialog("The entered project target value is not in a valid format!");
            return;
        }
        double targetAmount = Double.parseDouble(targetAmountString);
        SaveNewProjectThread saveNewProjectThread = new SaveNewProjectThread(new Project(name,description,targetAmount));
        Thread thread = new Thread(saveNewProjectThread);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        showInfoDialog("You successfully created new project!");

        noteTheChange("Project name: " +projectNameTextField.getText());
    }

    private void showInfoDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void noteTheChange(String name){
        String typeOfChange = "Creating a project.";
        String none = "None";
        NoteTheChange<String> change = new NoteTheChange<>("Donor",typeOfChange,none,name);
        Utils.saveChanges(change);
    }
}
