package com.mathias;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/menuutama.fxml"));
        primaryStage.setTitle("PBO2 #3 Praktikum");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
