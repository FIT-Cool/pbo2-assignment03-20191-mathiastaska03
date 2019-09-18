package com.mathias.controller;
/**
 *
 * @author mathias (1472054)
 */
import com.mathias.entity.category;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class categorycontroller implements Initializable {
    public TextField txtid;
    public TextField txtname;
    public Button botsave;
    public TableView<category> tabcate;
    public TableColumn<category,Integer> col1;
    public TableColumn<category,String> col2;

    private Maincontroller maincontroller;
    public void SaveAction(ActionEvent actionEvent) {
        category c = new category();
        c.setId(Integer.parseInt(txtid.getText().trim()));
        c.setNama(txtname.getText().trim());
        maincontroller.getCategories().add(c);
    }

    public void setMainFormController(Maincontroller maincontroller) {
        this.maincontroller = maincontroller;
        tabcate.setItems(maincontroller.getCategories());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        col1.setCellValueFactory(data->{
            category p = data.getValue();
            return new SimpleObjectProperty<>(p.getId());
        });
        col2.setCellValueFactory(data->{
            category c = data.getValue();
            return new SimpleStringProperty(c.getNama());
        });
    }
}
