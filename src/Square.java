import javafx.scene.paint.Color;

/**
 * Square shape.
 * @author Emily DeLisle
 * @version 1.0
 */
public class Square extends Shape {

    /**
     * Constructor for the Square
     * @param color Color value for this Rectangle
     */
    Square(Color color) {
        super(color);
    }

    /**
     * Determines which point (pointA or pointB) should be used as the starting point when drawing
     * the Square on the GraphicsContext. Since JavaFX only draws shapes down and to the right, if
     * the user is drawing up, this method tells the GraphicsContext to start drawing from
     * pointB instead (which is the point being dragged).
     * Overrides the super class method by first calculating the Y coordinate of the end point
     * (since the side lengths are the same)
     * @param y the Y Coordinate of the Cursor
     * @return which point to use
     */
    @Override
    double calculateStartY(double y) {
        calculateY(y);
        if (pointB[1] < pointA[1]) {
            return pointB[1];
        }
        return pointA[1];
    }

    /**
     * Clears the GraphicsContext and draws the Square on the GraphicsContext of the Canvas this
     * Rectangle is associated with.
     * @param x X coordinate of the Cursor
     * @param y Y coordinate of the Cursor
     */
    @Override
    void draw(double x, double y) {
        clear();
        gc.setFill(color);
        gc.fillRect(calculateStartX(x), calculateStartY(y), calculateSideLength(x), side);
    }

    /**
     * Clears the GraphicsContext and redraws the Square with the with its X and Y coordinates
     * translated by the specified distance.
     * @param distanceX distance translated on the X axis
     * @param distanceY distance translated on the Y axis
     */
    @Override
    void move(double distanceX, double distanceY) {
        super.move(distanceX, distanceY);
        gc.fillRect(topLeft[0], topLeft[1], side, side);
    }
}
