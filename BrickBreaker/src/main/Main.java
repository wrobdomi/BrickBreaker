package main;

import elements.Base;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../views/mainView.fxml"));
        primaryStage.setTitle("Brick Breaker");
        primaryStage.setScene(new Scene(root, 1500, 850));
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }


}
