package elements;


import javafx.scene.shape.Rectangle;

public class Base {

    private int width;
    private int height;
    private int initialX;
    private int initialY;
    private final int radius = 15;
    private final String baseId = "base";

    private Rectangle rectangle;


    public Base(int width, int height, int initialX, int initialY) {
        this.width = width;
        this.height = height;
        this.initialX = initialX;
        this.initialY = initialY;
        rectangle = new Rectangle();
        rectangle.setWidth(width);
        rectangle.setHeight(height);
        rectangle.setLayoutX(initialX);
        rectangle.setLayoutY(initialY);
        rectangle.setArcWidth(radius);
        rectangle.setArcHeight(radius);
        rectangle.setId(baseId);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public String getBaseId() {
        return baseId;
    }
}
