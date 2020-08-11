import javafx.scene.paint.Color;

/**
 * Oval shape.
 * @author Emily DeLisle
 * @version 1.0
 */
class Oval extends Shape {

    /**
     * Constructor for the Oval
     * @param color Color value for this Oval
     */
    Oval(Color color) {
        super(color);
    }

    /**
     * Clears the GraphicsContext and draws the Oval on the GraphicsContext of the Canvas this Oval
     * is associated with.
     * @param x X coordinate of the Cursor
     * @param y Y coordinate of the Cursor
     */
    @Override
    void draw(double x, double y) {
        clear();
        gc.setFill(color);
        gc.fillOval(calculateStartX(x), calculateStartY(y), calculateWidth(x), calculateHeight(y));
    }

    /**
     * Clears the GraphicsContext and redraws the Oval with the with its X and Y coordinates
     * translated by the specified distance.
     * @param distanceX distance translated on the X axis
     * @param distanceY distance translated on the Y axis
     */
    @Override
    void move(double distanceX, double distanceY) {
        super.move(distanceX, distanceY);
        gc.fillOval(topLeft[0], topLeft[1],
                bottomRight[0] - topLeft[0],
                bottomRight[1] - topLeft[1]);
    }
}
