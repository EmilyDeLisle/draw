import javafx.scene.paint.Color;
import java.util.ArrayList;

/**
 * Polygon shape.
 * @author Emily DeLisle
 * @version 1.0
 */
class Polygon extends Shape {

    /** ArrayList buffer that stores X coordinates as the Polygon is being drawn */
    private ArrayList<Double> xBuffer = new ArrayList<>();

    /** ArrayList buffer that stores Y coordinates as the Polygon is being drawn */
    private ArrayList<Double> yBuffer = new ArrayList<>();

    /** The Pen that is drawing the shape */
    private Pen pen;

    /** Boolean value that tells whether or not the Polygon has started being drawn yet */
    private boolean start = true;

    /**
     * Constructor for the Polygon
     * @param color Color value for this Polygon
     * @param pen the Pen that is drawing this Polygon. The Polygon needs a reference to the Pen so
     * that it can signal when the Polygon starts and ends being drawn
     */
    Polygon(Color color, Pen pen) {
        super(color);
        this.pen = pen;
    }

    /**
     * Drawing the Polygon first starts with signaling the Pen that there is a new Polygon being
     * drawn, which adds the Canvas and Shape to the Surface.
     * Coordinates of each click are stored in the X and Y buffers until there is at least three
     * points in the buffers and the Cursor is clicked very closely to one of the points in the
     * buffers. At that point, the buffers are converted to arrays of doubles and the total amount
     * of points is added up. This information is sent to the GraphicsContext, which fills the
     * shape.
     * @param x X coordinate of the cursor
     * @param y Y coordinate of the cursor
     */
    @Override
    void draw(double x, double y) {
        if (start) {
            pen.startPolygon();
            start = false;
        }
        gc.setFill(color);
        if (checkProximity(x, y) && xBuffer.size() >= 3) {
            clear();
            gc.setFill(pen.getColor());
            setColor(pen.getColor());
            gc.fillPolygon(createPointArray(xBuffer), createPointArray(yBuffer), xBuffer.size());
            pen.endPolygon();
        } else {
            if (xBuffer.size() >= 2) {
                // Creates a line while the Polygon is being drawn
                gc.setStroke(pen.getColor());
                gc.strokeLine(createPointArray(xBuffer)[xBuffer.size() - 2],
                        createPointArray(yBuffer)[xBuffer.size() - 2],
                        createPointArray(xBuffer)[xBuffer.size() - 1],
                        createPointArray(yBuffer)[xBuffer.size() - 1]);
            }
        }
    }

    /**
     * Converts the specified ArrayList buffer into an array of doubles.
     * @param p the ArrayList buffer
     * @return an equivalent array of doubles
     */
    private double[] createPointArray(ArrayList<Double> p) {
        double[] pa = new double[p.size()];
        for (int i = 0; i < p.size(); i++) {
            pa[i] = p.get(i);
        }
        return pa;
    }

    /**
     * Checks if the coordinates of the Mouse Press are within a close proximity to any of the
     * points in the X and Y buffers. If false, adds the point to the buffers.
     * @param x the X coordinate of the Cursor
     * @param y the Y coordinate of the Cursor
     * @return boolean true or false
     */
    private boolean checkProximity(double x, double y) {
        if (!xBuffer.isEmpty()) {
            for (int i = 0; i < xBuffer.size(); i++) {
                if (Math.abs(x - xBuffer.get(i)) < 5 && Math.abs(y - yBuffer.get(i)) < 5) {
                    return true;
                }
            }
        }
        xBuffer.add(x);
        yBuffer.add(y);
        return false;
    }

    /**
     * Clears the GraphicsContext and redraws the Polygon with the with its X and Y coordinates
     * translated by the specified distance.
     * @param distanceX distance translated on the X axis
     * @param distanceY distance translated on the Y axis
     */
    @Override
    void move(double distanceX, double distanceY) {
        clear();
        gc.setFill(color);

        // Recalculates where the corners of the Polygon are
        topLeft[0] -= distanceX;
        topLeft[1] -= distanceY;
        bottomRight[0] -= distanceX;
        bottomRight[1] -= distanceY;

        // Modifies the values in the buffers with the X and Y translations
        for (int i = 0; i < xBuffer.size(); i++) {
            xBuffer.set(i, xBuffer.get(i) - distanceX);
            yBuffer.set(i, yBuffer.get(i) - distanceY);
        }
        gc.fillPolygon(createPointArray(xBuffer), createPointArray(yBuffer), xBuffer.size());
    }

    /**
     * Finds the most extreme points (most to the left or right, highest and lowest) in the X and Y
     * buffers and assigns their values to the topLeft and bottomRight points so the Polygon can be
     * found.
     */
    @Override
    void determineCorners() {
        topLeft[0] = xBuffer.get(0);
        topLeft[1] = yBuffer.get(0);
        bottomRight[0] = xBuffer.get(0);
        bottomRight[1] = yBuffer.get(0);

        for (int i = 0; i < xBuffer.size(); i++) {
            if (xBuffer.get(i) < topLeft[0]) {
                topLeft[0] = xBuffer.get(i);
            }
            if (xBuffer.get(i) > bottomRight[0]) {
                bottomRight[0] = xBuffer.get(i);
            }
            if (yBuffer.get(i) < topLeft[1]) {
                topLeft[1] = yBuffer.get(i);
            }
            if (yBuffer.get(i) > bottomRight[1]) {
                bottomRight[1] = yBuffer.get(i);
            }
        }
    }
}
