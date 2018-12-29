package controllers;

import elements.Base;
import elements.Brick;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import java.net.URL;
import java.util.Random;
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

        showGame();

    }


    public void showGame(){
        System.out.println("Inside start");
        mainAnchor.getChildren().clear();
        drawBricks();
        drawBase();
    }

    @FXML
    public void changeMotiv(){
        showGame();
        System.out.println("Change motiv");
        String motiv = comboMotiv.getSelectionModel().getSelectedItem().toString();
        String lmotiv = motiv.toLowerCase();
        System.out.println("Motiv is " + lmotiv);
        lmotiv = lmotiv + "motiv.css";
        mainBorder.getStylesheets().clear();
        mainBorder.getStylesheets().add(lmotiv);
    }


    public void drawBricks(){

        int xPos = 50;
        int yPos = 50;
        int yRoadShift = 10;
        int yWinterShift = 20;
        Random random = new Random();

        String motiv = comboMotiv.getSelectionModel().getSelectedItem().toString();
        System.out.println("Motiv issss " + motiv);
        String brickColor = "#ffffff";

        switch (motiv){
            case "Maldives":
                brickColor = "#017CD9";
                break;
            case "Road":
                brickColor = "#BC9492";
                break;
            case "Winter":
                brickColor = "#CED1D6";
                break;
        }


        for(int i = 0 ; i < Brick.brickNum; i++){


            if(motiv.equals("Maldives") || motiv.equals("Stars")){
                if(xPos >= Brick.xLimit){
                    yPos += Brick.yShift;
                    xPos = 50;
                }
            }
            if(motiv.equals("Road")){
                if( i%4 == 0 && i != 0){
                    xPos += Brick.xShift;
                    yPos = 50 + ( ( i / 4 ) * yRoadShift);
                }
            }
            if(motiv.equals("Winter")){
                if( i%4 == 0 && i != 0){
                    xPos += Brick.xShift;
                    if(i < Brick.brickNum/2 ){
                        yPos = 50 + ( ( i / 4 ) * yWinterShift);
                    }
                    else{
                        yPos = 50 + ( ( Brick.brickNum / 8 ) * yWinterShift ) - ( ( (i - ( Brick.brickNum/2 ) )/ 4 ) * yWinterShift );
                    }
                }
            }


            if(motiv.equals("Stars")){
                brickColor = getRandomColor();
            }


            Brick brick = new Brick(50, 25, xPos, yPos, brickColor );


            if(motiv.equals("Maldives") || motiv.equals("Stars")){
                xPos += Brick.xShift;
            }
            if(motiv.equals("Road")){
                yPos = yPos + Brick.yShift;
            }
            if(motiv.equals("Winter")){
                yPos = yPos + Brick.yShift;
            }



            TranslateTransition tt =
                    new TranslateTransition(Duration.seconds(4), brick.getRectangle());

            int iniX = random.nextInt(1000);
            int iniY = random.nextInt(800);

            tt.setFromX( iniX);
            tt.setFromY( iniY);
            tt.setToX( brick.getRectangle().getTranslateX() );
            tt.setToY( brick.getRectangle().getTranslateY() );
            tt.play();

            mainAnchor.getChildren().add(brick.getRectangle());

        }
    }


    public void drawBase(){

        String color = baseColorPicker.getValue().toString();

        Base base = new Base((int) levelSlider.getValue(), 20, 500, 650, color);

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
        drawBase();
    }

    public String getRandomColor(){
        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        Color color = Color.rgb(r, g, b);
        return color.toString();
    }


}


