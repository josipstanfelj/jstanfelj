package com.example.donacijefx;

import domain.Project;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import threads.GetAllProjectsThread;
import java.util.List;

public class AllProjectsController {
    @FXML
    private TableView<Project> projectTableView;
    @FXML
    private TableColumn<Project,String> projectNameTableColumn;
    @FXML
    private TableColumn<Project,String> descriptionTableColumn;
    @FXML
    private TableColumn<Project,Double> targetAmountTableColumn;
    @FXML
    private TableColumn<Project,Double> currentAmountTableColumn;





    public void initialize(){

        projectNameTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Project, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Project, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getProjectName());
            }
        });
        descriptionTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Project, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Project, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getDescription());
            }
        });
        targetAmountTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Project, Double>, ObservableValue<Double>>() {
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Project, Double> param) {
                return new ReadOnlyObjectWrapper<>(param.getValue().getTargetAmount());
            }
        });

        currentAmountTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Project, Double>, ObservableValue<Double>>() {
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Project, Double> param) {
                return new ReadOnlyObjectWrapper<>(param.getValue().getCurrentAmount());
            }
        });

        GetAllProjectsThread getAllProjectsThread = new GetAllProjectsThread();
        Thread thread = new Thread(getAllProjectsThread);
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Project> projectList = getAllProjectsThread.getProjectList();

        if (!thread.isAlive()) {
            ObservableList<Project> donationsObservableList = FXCollections.observableList(projectList);
            projectTableView.setItems(donationsObservableList);
        }
    }
    }
