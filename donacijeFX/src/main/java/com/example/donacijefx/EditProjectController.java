package com.example.donacijefx;

import Exceptions.MissingSelectionException;
import domain.NoteTheChange;
import domain.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import threads.GetAllProjectsThread;
import threads.UpdateProjectDescriptionThread;
import threads.UpdateProjectNameThread;
import threads.UpdateProjectTargetAmountThread;
import utils.Utils;
import java.util.Optional;


public class EditProjectController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @FXML
    private ListView<Project> projectListView;

    @FXML
    private CheckBox projectNameCheckBox;
    @FXML
    private CheckBox targetAmountCheckBox;
    @FXML
    private CheckBox descriptionCheckBox;
    @FXML
    private TextField newProjectNameTextField;
    @FXML
    private TextField newTargetAmountTextField;
    @FXML
    private TextField newDescriptionTextField;
    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void initialize(){
        GetAllProjectsThread getAllProjectsThread = new GetAllProjectsThread();
        Thread thread = new Thread(getAllProjectsThread);
        thread.start();
        waitThread(thread);
        ObservableList<Project> donationList = FXCollections.observableArrayList(getAllProjectsThread.getProjectList());
        projectListView.setItems(donationList);
        projectListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void updateListView(){
        GetAllProjectsThread getAllProjectsThread = new GetAllProjectsThread();
        Thread thread = new Thread(getAllProjectsThread);
        thread.start();
        waitThread(thread);
        ObservableList<Project> donationList = FXCollections.observableArrayList(getAllProjectsThread.getProjectList());
        projectListView.setItems(donationList);
    }

    public void editProject(){
        try{
            if(projectListView.getSelectionModel().isEmpty()){
                throw new MissingSelectionException("You have to choose which donation you want to edit!");
            }
        }catch (MissingSelectionException ex){
            showInfoDialog(ex.getMessage());
            logger.info(ex.getMessage());
            return;
        }
        Project project = projectListView.getSelectionModel().getSelectedItem();
        if(!projectNameCheckBox.isSelected() && !targetAmountCheckBox.isSelected() && !descriptionCheckBox.isSelected()){
            showInfoDialog("Mark the checkboxes to be able to save the data!");
            return;
        }

        if(showConfirmationDialog("Are you sure you want make this changes?")){

            if(projectNameCheckBox.isSelected() && !newProjectNameTextField.getText().isEmpty()){
                String newName = newProjectNameTextField.getText();
                UpdateProjectNameThread updateProjectNameThread = new UpdateProjectNameThread(project.getProjectId(),newName);
                Thread thread = new Thread(updateProjectNameThread);
                thread.start();
                waitThread(thread);
                noteTheChange("Editing project name",project.getProjectName(),newName);
            }else if(projectNameCheckBox.isSelected() || !newProjectNameTextField.getText().isEmpty()){
                if(!projectNameCheckBox.isSelected()){
                    showInfoDialog("Mark new project name checkbox!");
                    return;
                } else{
                    showInfoDialog("Fill in the project name field!");
                    return;
                }
            }

            if(descriptionCheckBox.isSelected() && !newDescriptionTextField.getText().isEmpty()){
                String newDescription = newDescriptionTextField.getText();
                UpdateProjectDescriptionThread updateProjectDescriptionThread = new UpdateProjectDescriptionThread(project.getProjectId(),newDescription);
                Thread thread = new Thread(updateProjectDescriptionThread);
                thread.start();
                waitThread(thread);
                noteTheChange("Editing project description",project.getDescription(),newDescription);

            }else if(descriptionCheckBox.isSelected() || !newDescriptionTextField.getText().isEmpty()){
                if(!descriptionCheckBox.isSelected()){
                    showInfoDialog("Mark new project description checkbox!");
                    return;
                } else{
                    showInfoDialog("Fill in the project description field!");
                    return;
                }
            }
            if(targetAmountCheckBox.isSelected() && !newTargetAmountTextField.getText().isEmpty()){
                if(!isDouble(newTargetAmountTextField.getText())){
                    showInfoDialog("The entered value for target amount is not in a valid form!");
                    return;
                }
                double newTargetAmount = Double.parseDouble(newTargetAmountTextField.getText());

                UpdateProjectTargetAmountThread updateProjectTargetAmountThread = new UpdateProjectTargetAmountThread(project.getProjectId(),newTargetAmount);
                Thread thread = new Thread(updateProjectTargetAmountThread);
                thread.start();
                waitThread(thread);

                noteTheChange("Editing project target amount",String.valueOf(project.getTargetAmount()),String.valueOf(newTargetAmount));

            }else if(targetAmountCheckBox.isSelected() || !newTargetAmountTextField.getText().isEmpty()){
                if(!targetAmountCheckBox.isSelected()){
                    showInfoDialog("Mark new target amount checkbox!");
                    return;
                } else{
                    showInfoDialog("Fill in the target amount field!");
                    return;
                }
            }
            showInfoDialog("You successfully updated project data!");
            updateListView();
        }

    }

    private boolean showConfirmationDialog( String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Please confirm");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
    private void showInfoDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void noteTheChange(String typeOfChange,String before,String after){
        NoteTheChange<String> change = new NoteTheChange<>("Organization",typeOfChange,before,after);
        Utils.saveChanges(change);
    }
    public void waitThread(Thread thread){
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
