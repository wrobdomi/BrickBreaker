package elements;


import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Base {

    private static int width;
    private static int height;
    public static int initialX;
    public static int initialY;
    private final int radius = 15;
    private String color;

    public static int xUpperLimit;
    public static int xLowerLimit;

    public static final int moveLength;
    public static final String baseId;
    public static final String searchId;
    public static final int levelOneWidth;
    public static final int levelTwoWidth;
    public static final int levelThreeWidth;
    public static final int xUpperBound;
    public static final int xUpperBoundChange;

    public static int xCurrent;
    public static int yCurrent;

    private Rectangle rectangle;

    static {
        xUpperLimit = 1105;
        xLowerLimit = 0 ;
        moveLength = 20;
        baseId = "base";
        searchId = "#base";
        levelOneWidth = 250;
        levelTwoWidth = 175;
        levelThreeWidth = 100;
        xUpperBound = 1105;
        xUpperBoundChange = 75;
    }

    public Base(int widthLevel, int height, int initialX, int initialY, String color) {

        switch (widthLevel){
            case 1:
                this.width = this.levelOneWidth;
                this.xUpperLimit = xUpperBound - 2*xUpperBoundChange;
                break;
            case 2:
                this.width = this.levelTwoWidth;
                this.xUpperLimit = xUpperBound - xUpperBoundChange;
                break;
            case 3:
                this.width = this.levelThreeWidth;
                this.xUpperLimit = this.xUpperBound;
                break;
        }

        this.height = height;
        this.initialX = initialX;
        this.initialY = initialY;
        this.color = color;
        xCurrent = this.initialX;
        yCurrent = this.initialY;
        rectangle = new Rectangle();
        rectangle.setWidth(width);
        rectangle.setHeight(height);
        rectangle.setLayoutX(initialX);
        rectangle.setLayoutY(initialY);
        rectangle.setArcWidth(radius);
        rectangle.setArcHeight(radius);
        rectangle.setId(baseId);
        rectangle.setFill(Paint.valueOf(color));

    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight(){
        return height;
    }

}
