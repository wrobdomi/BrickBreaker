package controllers;

import elements.Ball;
import elements.Base;
import elements.Brick;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class BrickBreakerController implements Initializable {

    private ArrayList<Rectangle> bricks;
    int gameOver = 0;
    private int score = 0;

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

    @FXML
    private Label overLabel;

    @FXML
    private Label scoreLabel;

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
                Rectangle actualBase = (Rectangle) mainAnchor.lookup(Base.searchId);
                if(event.getCode() == KeyCode.RIGHT && actualBase.getLayoutX() < Base.xUpperLimit){
                    actualBase.setLayoutX(actualBase.getLayoutX() + Base.moveLength);
                    Base.xCurrent = Base.xCurrent + Base.moveLength;
                }
                else if(event.getCode() == KeyCode.LEFT && actualBase.getLayoutX() > Base.xLowerLimit ){
                    actualBase.setLayoutX(actualBase.getLayoutX() - Base.moveLength);
                    Base.xCurrent = Base.xCurrent - Base.moveLength;
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
        drawBall();
        Ball.goingUp = true;
        overLabel.setVisible(false);
        String s = score + " / 72";
        scoreLabel.setText(s);
        score = 0;
    }

    @FXML
    public void changeMotiv(){
        gameOver = 0;
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


        bricks = new ArrayList<>();

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


            bricks.add(brick.getRectangle());

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

    public void drawBall(){

        Ball ball;

        Circle circle = (Circle) mainAnchor.lookup(Ball.ballSearchId);
        if(circle == null){
            ball = new Ball();
        }else{
            mainAnchor.getChildren().remove(circle);
            ball = new Ball();
        }

        Circle circleBall = ball.getCircle();
        mainAnchor.getChildren().add(circleBall);

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

    public void startMovingBall(){
        if(gameOver == 1){
            showGame();
            startMovingBallAnim();
            gameOver = 0;
        }
        else{
            startMovingBallAnim();
        }
    }

    public void startMovingBallAnim(){

        System.out.println("Starting ball");

//        Circle circle = (Circle) mainAnchor.lookup(Ball.ballSearchId);
//        Rectangle rectangle = ( Rectangle ) mainAnchor.lookup(Base.searchId);

        Ball.currentX = Ball.initialX;
        Ball.currentY = Ball.initialY;
        gameOver = 0;


            ScheduledExecutorService execService = Executors.newScheduledThreadPool(5);
            execService.scheduleAtFixedRate(()->{

                Circle circle = (Circle) mainAnchor.lookup(Ball.ballSearchId);
                Rectangle rectangle = ( Rectangle ) mainAnchor.lookup(Base.searchId);
               // checkIntersectBrickAndBall();
                boolean brickEncountered = false;
                boolean fromUp = false;



                for(Rectangle r : bricks){
                    if(     r.getTranslateX() - 10 < circle.getTranslateX()
                            && r.getTranslateX() + r.getWidth() + 10 > circle.getTranslateX()
                            && r.getTranslateY() - r.getHeight()/2 < circle.getTranslateY()
                            && r.getTranslateY() + 3/2*r.getHeight() > circle.getTranslateY()
                            && r.isVisible() ) {
                        // System.out.println("Now removing " + r);
                        r.setVisible(false);
                        brickEncountered = true;
                        score++;
                        if( r.getTranslateY() > circle.getTranslateY() ){
                            fromUp = true;
                        }
                        break;
                    }
                }

                Ball.calculateBallDirection(Ball.currentX, Ball.currentY);


                int newX = 0;
                int newY = 0;

                if(!brickEncountered){
                    if(Ball.goingUp && Ball.goingLeft){
                        newX = Ball.currentX - Ball.xMovement;
                        newY = Ball.currentY - Ball.yMovement;
                        // System.out.println("Going Up and Left");
                    }
                    if(Ball.goingUp && !Ball.goingLeft){
                        newX = Ball.currentX + Ball.xMovement;
                        newY = Ball.currentY - Ball.yMovement;
                        // System.out.println("Going Up and Right");
                    }
                    if(!Ball.goingUp && Ball.goingLeft){
                        newX = Ball.currentX - Ball.xMovement;
                        newY = Ball.currentY + Ball.yMovement;
                        // System.out.println("Going Down and Left");
                    }
                    if(!Ball.goingUp && !Ball.goingLeft){
                        newX = Ball.currentX + Ball.xMovement;
                        newY = Ball.currentY + Ball.yMovement;
                        // System.out.println("Going Down and Right");
                    }
                    if(Ball.currentY > Base.initialY + Base.getHeight() ){
                        System.out.println("EXIT");
                        execService.shutdown();
                        gameOver = 1;
                        this.showGameOverDialog();
                    }
                }
               else{
                    System.out.println("Inside brickEncountered");
                    if(!fromUp){
                        System.out.println("From Down");
                        Ball.goingUp = false;
                        newY = Ball.currentY + Ball.yMovement;
                    }
                    else{
                        System.out.println("From Up");
                        Ball.goingUp = true;
                        newY = Ball.currentY - Ball.yMovement;
                    }
                    if(Ball.goingLeft){
                        newX = Ball.currentX - Ball.xMovement;
                    }else{
                        newX = Ball.currentX + Ball.xMovement;
                    }
                }

                // System.out.println("REC: " + rectangle.getLayoutX() + " " + rectangle.getLayoutY());
                // System.out.println("BALL: " + circle.getTranslateX() + " " + circle.getTranslateY());

                if(rectangle.getLayoutX() + Base.getWidth() > circle.getTranslateX()
                        && rectangle.getLayoutX() < circle.getTranslateX()
                        && rectangle.getLayoutY() - Base.getHeight()/2 < circle.getTranslateY()
                        && rectangle.getLayoutY() > circle.getTranslateY() ){
                    System.out.println("Intersect detected");
                    Ball.goingUp = true;
                    newY = Ball.currentY - Ball.yMovement;
                    if(Ball.goingLeft){
                        newX = Ball.currentX - Ball.xMovement;
                    }else{
                        newX = Ball.currentX + Ball.xMovement;
                    }
                }


                TranslateTransition tt =
                        new TranslateTransition(Duration.millis(75), circle);
                System.out.println(tt);
                tt.setFromX(Ball.currentX);
                tt.setFromY(Ball.currentY);
                tt.setToX( newX );
                tt.setToY( newY );
                tt.play();
                Ball.currentX = newX;
                Ball.currentY = newY;


            }, 0, 75L, TimeUnit.MILLISECONDS);


    }


    public void showGameOverDialog(){
        overLabel.setVisible(true);
    }

}


