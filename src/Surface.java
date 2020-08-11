import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import java.util.ArrayDeque;

/**
 * Surface. This is the drawing surface class, which extends from StackPane. Canvases are added
 * from the bottom up to the root. Also keeps track of the Shapes in the same order in an
 * ArrayDeque.
 * @author Emily DeLisle
 * @version 1.0
 */
class Surface extends StackPane {

    /** Stack of Shapes */
    ArrayDeque<Shape> shapes = new ArrayDeque<>();

    /**
     * Adds a new Canvas to the Surface's root.
     * @param c Canvas to add
     */
    void addNewCanvas(Canvas c) {
        this.getChildren().add(c);
    }

    /**
     * Adds a new Shape to the Surface's stack.
     * @param s the Shape to add
     */
    void addNewShape(Shape s) {
        shapes.addFirst(s);
    }

    /**
     * Removes a Shape from the Surface's stack.
     * @param s the Shape to remove.
     */
    void removeShape(Shape s) {
        if (!this.getChildren().isEmpty()) {
            this.getChildren().remove(s.getCanvas());
        }
        shapes.remove(s);
    }

    /**
     * Finds the first Shape in the stack (going down, starting from the top) that the specified X
     * and Y coordinates fall within.
     * @param x the X coordinate to search with
     * @param y the Y coordinate to search with
     * @return the Shape, if found. Returns null if no shape was found.
     */
    Shape findShape(double x, double y) {
        for (Shape s : shapes) {
            if (s.shapeExists(x, y)) {
                return s;
            }
        }
        return null;
    }

}
