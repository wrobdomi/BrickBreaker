package elements;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Brick {

    private int width;
    private int height;
    private int initialX;
    private int initialY;
    private String color;

    private Rectangle rectangle;

    public static int brickNum;
    public static int xShift;
    public static int yShift;
    public static int xLimit;

    static{
        brickNum = 72;
        xShift = 60;
        yShift = 80;
        xLimit = 1105;
    }

    public Brick(int width, int height, int initialX, int initialY, String color) {

        this.width = width;
        this.height = height;
        this.initialX = initialX;
        this.initialY = initialY;
        this.color = color;
        rectangle = new Rectangle();
        rectangle.setWidth(width);
        rectangle.setHeight(height);
        rectangle.setTranslateX(initialX);
        rectangle.setTranslateY(initialY);
        rectangle.setFill(Paint.valueOf(color));

    }

    public Rectangle getRectangle() {
        return rectangle;
    }

}
