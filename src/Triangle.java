import javafx.scene.paint.Color;

/**
 * Triangle shape.
 * @author Emily DeLisle
 * @version 1.0
 */
class Triangle extends Shape {

    /** X coordinates of the points of the Triangle */
    private double[] x = new double[3]; // side middle side

    /** Y coordinates of the points of the Triangle */
    private double[] y = new double[3]; // side middle side

    /** Total points of the Triangle */
    private final int numPoints = 3;

    /**
     * Constructor for the Triangle
     * @param color Color value for this Triangle
     */
    Triangle(Color color) {
        super(color);
    }

    /**
     * Calculates the X and Y coordinates of the points of the Triangle. The middle point is
     * calculating by finding the halfway point between the X coordinates of pointA and pointB.
     * @param x X coordinate of the Cursor
     * @param y Y coordinate of the Cursor
     */
    private void calculatePoints(double x, double y) {
        pointB[0] = x;
        pointB[1] = y;
        // side point X coordinate
        this.x[0] = pointA[0];
        // middle point X coordinate
        if (x < pointA[0]) {
            this.x[1] = pointA[0] - Math.abs(x - pointA[0])/2;
        } else {
            this.x[1] = pointA[0] + Math.abs(x - pointA[0])/2;
        }
        // side point X coordinate
        this.x[2] = x;

        // side point Y coordinate
        this.y[0] = pointA[1];
        // middle point Y coordinate
        this.y[1] = y;
        // side point Y coordinate
        this.y[2] = pointA[1];
    }

    /**
     * Clears the GraphicsContext and draws the Triangle on the GraphicsContext of the Canvas this
     * Triangle is associated with.
     * @param x X coordinate of the cursor
     * @param y Y coordinate of the cursor
     */
    @Override
    void draw(double x, double y) {
        clear();
        gc.setFill(color);
        calculatePoints(x, y);
        gc.fillPolygon(this.x, this.y, numPoints);
    }

    /**
     * Clears the GraphicsContext and redraws the Triangle with the with its X and Y coordinates
     * translated by the specified distance.
     * @param distanceX distance translated on the X axis
     * @param distanceY distance translated on the Y axis
     */
    @Override
    void move(double distanceX, double distanceY) {
        pointA[0] -= distanceX;
        pointA[1] -= distanceY;
        pointB[0] -= distanceX;
        pointB[1] -= distanceY;
        clear();
        x[0] -= distanceX;
        x[1] -= distanceX;
        x[2] -= distanceX;
        y[0] -= distanceY;
        y[1] -= distanceY;
        y[2] -= distanceY;
        clear();
        gc.fillPolygon(this.x, this.y, numPoints);
    }
}
