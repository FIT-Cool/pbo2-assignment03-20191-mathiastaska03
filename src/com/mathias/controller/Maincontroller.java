package com.mathias.controller;
/**
 *
 * @author mathias (1472054)
 */
import com.mathias.Main;
import com.mathias.entity.category;
import com.mathias.entity.item;
import com.sun.deploy.util.FXLoader;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class Maincontroller implements Initializable {
    public TextField TXTID;
    public TextField TXTNAME;
    public ComboBox<category> XOMBOCATE;
    public DatePicker DATEEXPIRE;
    public Button BOTSAVE;
    public Button BOTRESET;
    public Button BOTUPDATE;
    public TableView<item> TABVIEWTOKO;
    public TableColumn<item,Integer> COL1;
    public TableColumn<item,String> COL2;
    public TableColumn<item,category> COL3;
    public TableColumn<item,Date> COL4;
    private item selecteditem;
    private Maincontroller maincontroller;
    private ObservableList<category> categories;
    private ObservableList<item> items;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categories = FXCollections.observableArrayList();
        items = FXCollections.observableArrayList();
        XOMBOCATE.setItems(getCategories());
        DATEEXPIRE.setValue(LocalDate.now());
        TABVIEWTOKO.setItems(this.getItems());
        COL1.setCellValueFactory(data->{
            item p = data.getValue();
            return new SimpleObjectProperty<>(p.getId());
        });
        COL2.setCellValueFactory(data->{
            item p = data.getValue();
            return new SimpleStringProperty(p.getName());
        });
        COL3.setCellValueFactory(data->{
            item p = data.getValue();
            return new SimpleObjectProperty<>(p.getCategory());
        });
        COL4.setCellValueFactory(data->{
            item p = data.getValue();
            return new SimpleObjectProperty(asLocalDate(p.getExpiredate()));
        });

    }
    public void ShowCateACtion(ActionEvent actionEvent)  {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/Category.fxml"));
            VBox root = loader.load();
            categorycontroller controler = loader.getController();
            controler.setMainFormController(this);
            Stage mainStage = new Stage();
            mainStage.setTitle("Category form");
            mainStage.setScene(new Scene(root));
            mainStage.initModality(Modality.APPLICATION_MODAL);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void CloseAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void AboutAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Create by : mathias(1472054)");
        alert.setTitle("INFORMATION");
        alert.show();
    }

    public void saveAction(ActionEvent actionEvent) {
       int count = (int) items.stream().filter(p-> p.getName().equalsIgnoreCase(TXTNAME.getText())).count();
        if (TXTID.getText().isEmpty() || TXTNAME.getText().isEmpty()||XOMBOCATE.getValue()==null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill name/price/category");
            alert.showAndWait();
        } else if (count >= 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Duplicate name");
            alert.showAndWait();
       }else{
            items.add(new item(
                    Integer.valueOf(TXTID.getText()),TXTNAME.getText(),
                    asDate(DATEEXPIRE.getValue()),XOMBOCATE.getValue()));
            System.out.println(items);
            resetAction(actionEvent);
            TABVIEWTOKO.refresh();
        }
    }

    public void resetAction(ActionEvent actionEvent) {
        TXTNAME.clear();
        TXTID.clear();
        XOMBOCATE.setValue(null);
        DATEEXPIRE.setValue(null);
    }

    public void updateAction(ActionEvent actionEvent) {
        int count = (int) items.stream().filter(p-> p.getName().equalsIgnoreCase(TXTNAME.getText())).count();
        if (!TXTNAME.getText().isEmpty()||!TXTID.getText().isEmpty()|| XOMBOCATE.getValue()!=null){
            selecteditem.setId(Integer.valueOf(TXTID.getText()));
            selecteditem.setName(TXTNAME.getText());
            selecteditem.setCategory(XOMBOCATE.getValue());
            selecteditem.setExpiredate(asDate(DATEEXPIRE.getValue()));
        } else if (TXTID.getText().isEmpty() || TXTNAME.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill name/price/category");
            alert.showAndWait();
        } else if (count >= 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Duplicate name");
            alert.showAndWait();
        }
        BOTSAVE.setDisable(false);
        BOTUPDATE.setDisable(true);
        resetAction(actionEvent);
        TABVIEWTOKO.refresh();

    }

    public void tabActionClick(MouseEvent mouseEvent) {
        selecteditem = TABVIEWTOKO.getSelectionModel().getSelectedItem();
        if (selecteditem!=null){
            TXTID.setText(Integer.toString(TABVIEWTOKO.getSelectionModel().getSelectedItem().getId()));
            TXTNAME.setText(TABVIEWTOKO.getSelectionModel().getSelectedItem().getName());
            XOMBOCATE.setValue(TABVIEWTOKO.getSelectionModel().getSelectedItem().getCategory());
            DATEEXPIRE.setValue(asLocalDate(TABVIEWTOKO.getSelectionModel().getSelectedItem().getExpiredate()));
            BOTUPDATE.setDisable(false);
            BOTSAVE.setDisable(true);
        }
    }

    public ObservableList<category> getCategories(){
        if (categories==null){
            categories = FXCollections.observableArrayList();
        }
        return categories;
    }
    public ObservableList<item> getItems(){
        if (items==null){
            items = FXCollections.observableArrayList();
        }
        return items;
    }

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
