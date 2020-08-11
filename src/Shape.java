import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Shape. This is the abstract parent class from which all of the other shapes extend. This abstract
 * class handles most of necessary functions needed for calculating how the shape is drawn. New
 * shapes can easily be extended from this superclass by overriding the draw() and move() methods.
 * @author Emily DeLisle
 * @version 1.0
 */
abstract class Shape {

    /** Starting point */
    double[] pointA = new double[2];

    /** Ending point */
    double[] pointB = new double[2];

    /** Top left point used for finding the shape. Can point to either PointA or pointB */
    double[] topLeft = new double[2];

    /** Bottom right point used for finding the shape. Can point to either PointA or pointB */
    double[] bottomRight = new double[2];

    /** The Color value of this shape */
    Color color;

    /** Canvas associated with this Shape. */
    private Canvas canvas;

    /** The GraphicsContext object associated with this Shape's Canvas */
    GraphicsContext gc;

    /** The side length used for some Shapes */
    double side;

    /**
     * Constructor for the Shape.
     * @param color Color value for this Shape
     */
    Shape(Color color) {
        canvas = new Canvas(1000, 780);
        gc = canvas.getGraphicsContext2D();
        this.color = color;
    }

    /**
     * Draws the Shape on the GraphicsContext of the Canvas this Shape is associated with.
     * Abstract method because every Shape is drawn differently.
     * @param x X coordinate of the cursor
     * @param y Y coordinate of the cursor
     */
    abstract void draw(double x, double y);

    /**
     * Clears the GraphicsContext and redraws the Shape with the with its X and Y coordinates
     * translated by the specified distance.
     * Other shapes call this super class method before they call their own move() method, as the
     * general process of the move is mostly the same, but how the Shape is redrawn when moved is
     * different for each Shape.
     * @param distanceX distance translated on the X axis
     * @param distanceY distance translated on the Y axis
     */
    void move(double distanceX, double distanceY) {
        clear();
        topLeft[0] -= distanceX;
        topLeft[1] -= distanceY;
        bottomRight[0] -= distanceX;
        bottomRight[1] -= distanceY;
        pointA[0] = topLeft[0];
        pointB[0] = bottomRight[0];
        pointA[1] = topLeft[1];
        pointB[1] = bottomRight[1];
        gc.setFill(color);
    }

    /**
     * Gets the Canvas associated with this Shape.
     * @return the Canvas
     */
    Canvas getCanvas() {
        return canvas;
    }

    /** Clears the GraphicsContext by creating a clear rectangle on the Canvas the same size as the
     * Canvas */
    void clear() {
        gc.clearRect(0, 0, 1000, 780);
    }

    /**
     * Calculates the width of the Shape by finding the X-axis distance between the starting point
     * (pointA) and the end point (pointB).
     * @param x the X coordinate of the Cursor
     * @return the distance on the X axis
     */
    double calculateWidth(double x) {
        return Math.abs(x - pointA[0]);
    }

    /**
     * Calculates the height of the Shape by finding the Y-axis distance between the starting point
     * (pointA) and the end point (pointB).
     * @param y the Y coordinate of the Cursor
     * @return the distance on the Y axis
     */
    double calculateHeight(double y) {
        return Math.abs(y - pointA[1]);
    }

    /**
     * Sets the coordinates of pointA (the starting point) when the user first clicks on the drawing
     * area.
     * @param x the X coordinate of the Cursor
     * @param y the Y coordinate of the Cursor
     */
    void setStartCoords(double x, double y) {
        pointA[0] = x;
        pointA[1] = y;
    }

    /**
     * Determines which point (pointA or pointB) should be used as the starting point when drawing
     * the shape on the GraphicsContext. Since JavaFX only draws shapes down and to the right, if
     * the user is drawing to the left, this method tells the GraphicsContext to start drawing from
     * pointB instead (which is the point being dragged).
     * @param x the X Coordinate of the Cursor
     * @return which point to use
     */
    double calculateStartX(double x) {
        pointB[0] = x;
        if (pointB[0] < pointA[0]) {
            return pointB[0];
        }
        return pointA[0];
    }

    /**
     * Determines which point (pointA or pointB) should be used as the starting point when drawing
     * the shape on the GraphicsContext. Since JavaFX only draws shapes down and to the right, if
     * the user is drawing up, this method tells the GraphicsContext to start drawing from
     * pointB instead (which is the point being dragged).
     * @param y the Y Coordinate of the Cursor
     * @return which point to use
     */
    double calculateStartY(double y) {
        pointB[1] = y;
        if (pointB[1] < pointA[1]) {
            return pointB[1];
        }
        return pointA[1];
    }

    /**
     * Calculates the side length for Shapes that have side lengths locked together (Square and
     * Circle)
     * @param x the X coordinate of the Cursor
     * @return the length of the side
     */
    double calculateSideLength(double x) {
        side = calculateWidth(x);
        return side;
    }

    /**
     * Adds or removes the side length distance to the Y-axis of the shape. Used for Shapes that
     * have side lengths locked together (Square and Circle)
     * @param y the Y coordinate of the Cursor
     */
    void calculateY(double y) {
        pointB[1] = pointA[1];
        if (y < pointA[1]) {
            pointB[1] -= side;
        } else {
            pointB[1] += side;
        }
    }

    /**
     * Determines which of pointA and pointB are the top left and bottom right corners. Used for
     * finding the Shape.
     */
    void determineCorners() {
        if (pointA[0] < pointB[0]) {
            topLeft[0] = pointA[0];
            bottomRight[0] = pointB[0];
        } else {
            bottomRight[0] = pointA[0];
            topLeft[0] = pointB[0];
        }
        pointA[0] = topLeft[0];
        pointB[0] = bottomRight[0];
        if (pointA[1] < pointB[1]) {
            topLeft[1] = pointA[1];
            bottomRight[1] = pointB[1];
        } else {
            bottomRight[1] = pointA[1];
            topLeft[1] = pointB[1];
        }
        pointA[1] = topLeft[1];
        pointB[1] = bottomRight[1];
    }

    /**
     * First calls determineCorners() to find which points are the outer limits of the shape. Then
     * uses the coordinates of the Cursor click to determine if the click is within those limits.
     * @param x the X coordinate of the Cursor
     * @param y the Y coordinate of the Cursor
     * @return the Shape, if found. Null if not found.
     */
    boolean shapeExists(double x, double y) {
        determineCorners();
        return (x >= topLeft[0] && x <= bottomRight[0])
                && (y >= topLeft[1] && y <= bottomRight[1]);
    }

    /**
     * Sets the Shape's color value.
     * @param c the Color value to set
     */
    void setColor(Color c) {
        this.color = c;
    }
}
