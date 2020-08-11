import javafx.scene.paint.Color;

/**
 * Pen class, used for MouseEvents.
 * @author Emily DeLisle
 * @version 1.0
 */
class Pen {

    /** The Surface that holds the Shapes and GraphicsContext objects the Shapes are drawn on */
    Surface surface;

    /** The currently-selected Color value */
    private Color color = Color.RED;

    /** The currently-active Shape */
    Shape shape;

    /** The Shape that will be drawn */
    private Shape shapeToDraw = new Rectangle(color);

    /** The starting coordinates of a MouseDragged event */
    private double[] startCoords = new double[2];

    /**
     * Constructor for the Pen. Sets up the reference to the Surface that holds the Shapes and
     * initializes with Drawing mode.
     * @param surface the Surface to store Shapes
     */
    Pen (Surface surface) {
        this.surface = surface;
        initDraw();
    }

    /**
     * Initializes Draw mode. Sets up three different Mouse Events:
     * 1. MousePressed - calls setShape() to initialize the current Shape type
     * 2. MouseDragged - calls the current Shape's draw() method
     * 3. MouseReleased - creates a new Shape so that subsequent MousePress and MouseDrag events
     * will create new Shapes each time
     */
    void initDraw() {
        setShape();
        surface.setOnMouseDragged(event -> shape.draw(event.getX(), event.getY()));
        surface.setOnMouseReleased(event -> {
            newShape();
            shape = surface.shapes.getFirst();
        });
    }

    /**
     * Initializes Move/Select mode. Sets up three different Mouse Events:
     * 1. MousePressed - tries to find the Shape at the location the mouse was pressed. If found,
     *    gets the coordinates of the press.
     * 2. MouseDragged - if the initial press found a Shape, calculates the distance between the
     *    coordinates of the initial mouse press and the current position, then redraws the shape
     *    being moved translated that distance.
     * 3. MouseReleased - removes the MouseEvent handler set up with initDraw()
     */
    void initSelect() {
        surface.setOnMousePressed(event -> {
            shape = surface.findShape(event.getX(), event.getY());
            if (shape != null) {
                startCoords[0] = event.getX();
                startCoords[1] = event.getY();
            }
        });
        surface.setOnMouseDragged(event -> {
            if (shape != null) {
                shape.move(calculateXDistance(event.getX()), calculateYDistance(event.getY()));
                startCoords[0] = event.getX();
                startCoords[1] = event.getY();
            }
        });
        surface.setOnMouseReleased(null);
    }

    /**
     * Calculates the distance between the Cursor's X coordinate and the X coordinate of the
     * initial Mouse Press.
     * @param x the X coordinate of the Cursor
     * @return the distance between the Cursor's X coordinate and the X coordinate of the initial
     * Mouse Press.
     */
    private double calculateXDistance(double x) {
        return startCoords[0] - x;
    }

    /**
     * Calculates the distance between the Cursor's Y coordinate and the Y coordinate of the
     * initial Mouse Press.
     * @param y the X coordinate of the Cursor
     * @return the distance between the Cursor's Y coordinate and the Y coordinate of the initial
     * Mouse Press.
     */
    private double calculateYDistance(double y) {
        return startCoords[1] - y;
    }

    /** Removes a shape from the Surface's Stack */
    void deleteShape() {
        surface.removeShape(shape);
    }

    /**
     * Sets up the MousePressed event for initDraw(). Points the currently active shape to the
     * shape to be drawn, then creates a new Shape. Adds that shape to the Surface's Stack and root.
     * Finally, sets the starting coordinates (pointA) of the new Shape.
     */
    private void setShape() {
        surface.setOnMousePressed(event -> {
            shape = shapeToDraw;
            newShape();
            surface.addNewShape(shape);
            surface.addNewCanvas(shape.getCanvas());
            shape.setStartCoords(event.getX(), event.getY());
        });
    }

    /** Sets the Shape to be drawn to be a Rectangle, then reinitializes Draw mode */
    void setRectangle() {
        shapeToDraw = new Rectangle(color);
        initDraw();
    }

    /** Sets the Shape to be drawn to be a Square, then reinitializes Draw mode */
    void setSquare() {
        shapeToDraw = new Square(color);
        initDraw();
    }

    /** Sets the Shape to be drawn to be an Oval, then reinitializes Draw mode */
    void setOval() {
        shapeToDraw = new Oval(color);
        initDraw();
    }

    /** Sets the Shape to be drawn to be a Circle, then reinitializes Draw mode */
    void setCircle() {
        shapeToDraw = new Circle(color);
        initDraw();
    }

    /** Sets the Shape to be drawn to be a Triangle, then reinitializes Draw mode */
    void setTriangle() {
        shapeToDraw = new Triangle(color);
        initDraw();
    }

    /**
     * Sets the Shape to be drawn to be a Polygon. Sets up the MouseEvents slightly differently than
     * the other Shapes, since the Polygon is drawn with multiple mouse presses, rather than mouse
     * drags. Removes the MouseEvent drag and release events set up with initDraw().
     */
    void setPolygon() {
        shapeToDraw = new Polygon(color, this);
        shape = shapeToDraw;
        surface.setOnMousePressed(event -> {
            shape = shapeToDraw;
            shape.draw(event.getX(), event.getY());
        });
        surface.setOnMouseDragged(null);
        surface.setOnMouseReleased(null);
    }

    /** Starts a Polygon by adding it to the Surface's Stack and root. */
    void startPolygon() {
        surface.addNewShape(shape);
        surface.addNewCanvas(shape.getCanvas());
    }

    /** Ends a Polygon by creating a new Polygon shape and setting the currently-shape to it. */
    void endPolygon() {
        shapeToDraw = new Polygon(color, this);
        shape = shapeToDraw;
    }

    /**
     * Creates a new instance of the currently-active Shape object by examining what the Shape is,
     * then creating a new one.
     */
    private void newShape() {
        if (shape instanceof Polygon) {
            shape = new Polygon(color, this);
        } else if (shape instanceof Square) {
            shape = new Square(color);
        } else if (shape instanceof Oval) {
            shape = new Oval(color);
        } else if (shape instanceof Circle) {
            shape = new Circle(color);
        } else if (shape instanceof Triangle) {
            shape = new Triangle(color);
        } else {
            shape = new Rectangle(color);
        }
    }

    /**
     * Sets the current Color value.
     * @param c the Color to set
     */
    void setColor(Color c) {
        color = c;
    }

    /**
     * Gets the current Color value.
     * @return the current Color value
     */
    Color getColor() {
        return color;
    }
}
