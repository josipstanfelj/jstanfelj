package com.example.donacijefx;

import domain.NoteTheChange;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import utils.Utils;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ChangeLogController {

    @FXML
    private TableView<NoteTheChange<String>> changesTableView;
    @FXML
    private TableColumn<NoteTheChange<String>,String> roleTableColumn;
    @FXML
    private TableColumn<NoteTheChange<String>,String> dateTableColumn;
    @FXML
    private TableColumn<NoteTheChange<String>,String> tyopeOfChangeTableColumn;
    @FXML
    private TableColumn<NoteTheChange<String>,String> valueBeforeTableColumn;
    @FXML
    private TableColumn<NoteTheChange<String>,String> valueAfterTableColumn;


    public void initialize(){

        List<NoteTheChange<String>> noteChangesList = Utils.getAllChanges();


        roleTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NoteTheChange<String>, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<NoteTheChange<String>, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getRole());
            }
        });

        dateTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NoteTheChange<String>,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<NoteTheChange<String>,String> param) {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                return new ReadOnlyStringWrapper(param.getValue().getLocalDateTime().format(dateTimeFormatter));
            }
        });

        tyopeOfChangeTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NoteTheChange<String>,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<NoteTheChange<String>,String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getTypeOfChange());
            }
        });
        valueBeforeTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NoteTheChange<String>,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<NoteTheChange<String>,String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getValueBefore());
            }
        });
        valueAfterTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NoteTheChange<String>,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<NoteTheChange<String>,String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getValueAfter());
            }
        });

        ObservableList<NoteTheChange<String>> noteChangesObservableList = FXCollections.observableList(noteChangesList);
        changesTableView.setItems(noteChangesObservableList);

    }


}
