package controllers;

import elements.Base;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;


public class BrickBreakerController implements Initializable {

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private Button startButton;

    @FXML
    private ComboBox comboMotiv;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboMotiv.getItems().removeAll(comboMotiv.getItems());
        comboMotiv.getItems().addAll("Maldives", "Road", "Winter", "Stars");
        comboMotiv.getSelectionModel().select("Maldives");

        drawBase();
        mainAnchor.getStylesheets().add("maldivesmotiv.css");
    }

    @FXML
    public void startGame(){

    }

    @FXML
    public void changeMotiv(){
        System.out.println("Change motiv");
        String motiv = comboMotiv.getSelectionModel().getSelectedItem().toString();
        String lmotiv = motiv.toLowerCase();
        System.out.println("Motiv is " + lmotiv);
        lmotiv = lmotiv + "motiv.css";
        mainAnchor.getStylesheets().clear();
        mainAnchor.getStylesheets().add(lmotiv);
    }


    public void drawBricks(){


    }


    public void drawBase(){
        Base base = new Base(100, 20, 450, 650 );

        Rectangle rectangleBase = base.getRectangle();

        Rectangle actualBase = (Rectangle) mainAnchor.lookup(base.getBaseId());
        if(actualBase != null){
            mainAnchor.getChildren().remove(actualBase);
        }

        mainAnchor.getChildren().add(rectangleBase);

        TranslateTransition tt =
                new TranslateTransition(Duration.seconds(4), rectangleBase);

        tt.setFromX( 400 );
        tt.setToX( rectangleBase.getTranslateX() );
        tt.setToY( rectangleBase.getTranslateY() );
        tt.play();
    }



}


