package elements;

import javafx.scene.shape.Circle;

public class Ball {

    private static Circle circle;

    public static final int initialX = 570;
    public static final int initialY = 590;
    private static final int radius = 10;

    public static final String ballId = "ball";
    public static final String ballSearchId = "#ball";

    public static int currentX;
    public static int currentY;

    public static final int xAreaLowBorder = 20;
    public static final int xAreaHighBorder = 1200;
    public static final int yAreaLowBorder = 650;
    public static final int yAreaHighBorder = 20;

    public static final int xMovement = 20;
    public static final int yMovement = 20;

    public static boolean goingUp = true;
    public static boolean goingLeft = true;


    public Ball() {
        circle = new Circle();
        circle.setTranslateX(initialX);
        circle.setTranslateY(initialY);
        circle.setRadius(radius);
        circle.setId(ballId);
    }

    public Circle getCircle() {
        return circle;
    }

    public static void calculateBallDirection(int x, int y){
        if(x <= xAreaLowBorder ){
            goingLeft = false;
            // System.out.print("calculate Right");
        }
        if(x >= xAreaHighBorder){
            goingLeft = true;
            // System.out.print("calculate Left");
        }
        if(y <= yAreaHighBorder){
            goingUp = false;
            // System.out.println("calculate Down");
        }
    }

}

