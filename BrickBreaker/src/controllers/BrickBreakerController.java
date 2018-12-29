package controllers;

import elements.Base;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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

    @FXML
    private VBox menuBox;

    @FXML
    private BorderPane mainBorder;

    @FXML
    private ColorPicker baseColorPicker;

    @FXML
    private Slider levelSlider;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboMotiv.getItems().removeAll(comboMotiv.getItems());
        comboMotiv.getItems().addAll("Maldives", "Road", "Winter", "Stars");
        comboMotiv.getSelectionModel().select("Maldives");

        baseColorPicker.setValue(Color.valueOf("#ffffff"));

        drawBase();

        mainBorder.getStylesheets().add("maldivesmotiv.css");

        mainBorder.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println("Inside key handler");
                Rectangle actualBase = (Rectangle) mainAnchor.lookup(Base.searchId);
                System.out.println(actualBase);
                if(event.getCode() == KeyCode.RIGHT && actualBase.getLayoutX() < Base.xUpperLimit){
                    actualBase.setLayoutX(actualBase.getLayoutX() + Base.moveLength);
                }
                else if(event.getCode() == KeyCode.LEFT && actualBase.getLayoutX() > Base.xLowerLimit ){
                    actualBase.setLayoutX(actualBase.getLayoutX() - Base.moveLength);
                }
                event.consume();
            }
        });

    }

    @FXML
    public void startGame(){
        System.out.println("Inside start");

    }

    @FXML
    public void changeMotiv(){
        System.out.println("Change motiv");
        String motiv = comboMotiv.getSelectionModel().getSelectedItem().toString();
        String lmotiv = motiv.toLowerCase();
        System.out.println("Motiv is " + lmotiv);
        lmotiv = lmotiv + "motiv.css";
        mainBorder.getStylesheets().clear();
        mainBorder.getStylesheets().add(lmotiv);
    }


    public void drawBricks(){


    }


    public void drawBase(){

        String color = baseColorPicker.getValue().toString();

        Base base = new Base(1, 20, 500, 650, color);

        Rectangle rectangleBase = base.getRectangle();

        Rectangle actualBase = (Rectangle) mainAnchor.lookup(Base.searchId);
        System.out.println(actualBase);
        if(actualBase != null){
            mainAnchor.getChildren().remove(actualBase);
        }

        mainAnchor.getChildren().add(rectangleBase);

        TranslateTransition tt =
                new TranslateTransition(Duration.seconds(4), rectangleBase);

        tt.setFromX( 0 );
        tt.setToX( rectangleBase.getTranslateX() );
        tt.setToY( rectangleBase.getTranslateY() );
        tt.play();
    }


    public void drawBase(int level){

        String color = baseColorPicker.getValue().toString();

        Base base = new Base(level, 20, 500, 650, color);

        Rectangle rectangleBase = base.getRectangle();

        Rectangle actualBase = (Rectangle) mainAnchor.lookup(Base.searchId);
        System.out.println(actualBase);
        if(actualBase != null){
            mainAnchor.getChildren().remove(actualBase);
        }

        mainAnchor.getChildren().add(rectangleBase);

        TranslateTransition tt =
                new TranslateTransition(Duration.seconds(4), rectangleBase);

        tt.setFromX( 0 );
        tt.setToX( rectangleBase.getTranslateX() );
        tt.setToY( rectangleBase.getTranslateY() );
        tt.play();
    }


    public void changeLevel(){
        drawBase((int) levelSlider.getValue());
    }


}


