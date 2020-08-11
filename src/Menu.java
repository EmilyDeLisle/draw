import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * The Menu Pane for the Draw application. Extends from HBox, so all elements automatically stack
 * horizontally. Contains two inner classes: Buttons (extended from HBox) and Infobar (extended
 * from HBox). The Menu is a flexible design as new Buttons or functions can very easily be added to
 * the HBox and they will stack automatically.
 * @author Emily DeLisle
 * @version 1.0
 */
class Menu extends HBox {

    /** Contains all of the GUI buttons for the Draw application. */
    class Buttons extends HBox {

        /** Constructor for the Buttons class */
        Buttons() {
            init();
        }

        /** Initializes all of the display and functionality of the GUI buttons */
        private void init() {
            // Draw button
            Image drawIcon = new Image("images/draw.png");
            ImageView drawView = new ImageView(drawIcon);
            drawView.setFitWidth(20);
            drawView.setPreserveRatio(true);
            Button drawButton = new Button("", drawView);

            // Move/Select button
            Image moveIcon = new Image("images/move.png");
            ImageView moveView = new ImageView(moveIcon);
            moveView.setFitWidth(20);
            moveView.setPreserveRatio(true);
            Button moveButton = new Button("", moveView);

            // Rectangle button
            Image rectIcon = new Image("images/rectangle.png");
            ImageView rectView = new ImageView(rectIcon);
            rectView.setFitWidth(20);
            rectView.setPreserveRatio(true);
            Button rectButton = new Button("", rectView);

            // Square button
            Image sqIcon = new Image("images/square.png");
            ImageView sqView = new ImageView(sqIcon);
            sqView.setFitWidth(20);
            sqView.setPreserveRatio(true);
            Button sqButton = new Button("", sqView);

            // Oval button
            Image ovalIcon = new Image("images/oval.png");
            ImageView ovalView = new ImageView(ovalIcon);
            ovalView.setFitWidth(20);
            ovalView.setPreserveRatio(true);
            Button ovalButton = new Button("", ovalView);

            // Circle button
            Image circIcon = new Image("images/circle.png");
            ImageView circView = new ImageView(circIcon);
            circView.setFitWidth(20);
            circView.setPreserveRatio(true);
            Button circButton = new Button("", circView);

            // Triangle button
            Image triIcon = new Image("images/triangle.png");
            ImageView triView = new ImageView(triIcon);
            triView.setFitWidth(20);
            triView.setPreserveRatio(true);
            Button triButton = new Button("", triView);

            // Polygon button
            Image polyIcon = new Image("images/star.png");
            ImageView polyView = new ImageView(polyIcon);
            polyView.setFitWidth(20);
            polyView.setPreserveRatio(true);
            Button polyButton = new Button("", polyView);

            // ColorPicker button
            ColorPicker colorPicker = new ColorPicker();
            colorPicker.setValue(Color.RED);
            colorPicker.setStyle("-fx-color-label-visible: false ;");
            colorPicker.setMinSize(50, 30);

            // Delete button
            Image deleteIcon = new Image("images/delete.png");
            ImageView deleteView = new ImageView(deleteIcon);
            deleteView.setFitWidth(20);
            deleteView.setPreserveRatio(true);
            Button deleteButton = new Button("", deleteView);

            this.getChildren().addAll(moveButton, rectButton, sqButton,
                    ovalButton, circButton, triButton, polyButton, colorPicker);

            // Changes the cursor to a hand icon when the buttons are hovered over
            this.setOnMouseEntered(event -> scene.setCursor(Cursor.HAND));
            this.setOnMouseExited(event -> scene.setCursor(Cursor.DEFAULT));

            // Button functionality
            drawButton.setOnMouseClicked(event -> {
                pen.initDraw();
                this.getChildren().removeAll(drawButton, deleteButton);
                this.getChildren().addAll(moveButton, rectButton, sqButton,
                        ovalButton, circButton, triButton, polyButton, colorPicker);
                toolText.setText("Draw");
                shapeText.setVisible(true);
                currentShapeText.setVisible(true);
            });
            moveButton.setOnMouseClicked(event -> {
                pen.initSelect();
                this.getChildren().addAll(drawButton, deleteButton);
                this.getChildren().removeAll(moveButton, rectButton, sqButton,
                        ovalButton, circButton, triButton, polyButton, colorPicker);
                toolText.setText("Select/Move");
                shapeText.setVisible(false);
                currentShapeText.setVisible(false);
            });
            // Deletes the Pen's current shape, then gives the Pen a reference to the next Shape in
            // the stack (if one exists)
            deleteButton.setOnMouseClicked(event -> {
                if (pen.shape != null) {
                    pen.deleteShape();
                    if (!pen.surface.shapes.isEmpty()) {
                        pen.shape = pen.surface.shapes.getFirst();
                    }
                }
            });

            // Button functionality
            rectButton.setOnMouseClicked(event -> {
                pen.setRectangle();
                shapeText.setText("Rectangle");
            });
            sqButton.setOnMouseClicked(event -> {
                pen.setSquare();
                shapeText.setText("Square");
            });
            ovalButton.setOnMouseClicked(event-> {
                pen.setOval();
                shapeText.setText("Oval");
            });
            circButton.setOnMouseClicked(event -> {
                pen.setCircle();
                shapeText.setText("Circle");
            });
            triButton.setOnMouseClicked(event -> {
                pen.setTriangle();
                shapeText.setText("Triangle");
            });
            polyButton.setOnMouseClicked(event -> {
                pen.setPolygon();
                shapeText.setText("Polygon");
            });
            colorPicker.setOnAction(event -> pen.setColor(colorPicker.getValue()));

            // Adds a key press listener to allow the ability to delete the currently selected
            // shape by hitting the delete or backspace key
            scene.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.DELETE || event.getCode() == KeyCode.BACK_SPACE) {
                    pen.deleteShape();
                }
            });
        }
    }

    /** Contains textual information about the Draw application */
    class Infobar extends HBox {

        /** Constructor for the Infobar */
        Infobar() {
            currentShapeText = new Text("Current Shape:");
            shapeText = new Text("Rectangle");
            Text currentToolText = new Text("Current Tool:");
            toolText = new Text("Draw");
            currentToolText.setFont(Font.font("Verdana", FontWeight.LIGHT, 18));
            toolText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
            currentShapeText.setFont(Font.font("Verdana", FontWeight.LIGHT, 18));
            shapeText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
            this.setPadding(new Insets(2, 10, 0, 10));
            this.setSpacing(10);
            this.getChildren().addAll(currentShapeText, shapeText, currentToolText, toolText);
        }
    }

    /** Infobar text display */
    private Text currentShapeText;

    /** Current shape Infobar text display */
    private Text shapeText;

    /** Current toolText Infobar text display */
    private Text toolText;

    /** Pen object which handles mouse events */
    private Pen pen;

    /** Scene for the Application */
    private Scene scene;

    /**
     * Constructor for the Menu. Initializes the inner class sub menus and adds them to the Menu
     * root.
     */
    Menu(Pen pen, Scene scene) {
        this.pen = pen;
        this.scene = scene;
        Region space = new Region();
        HBox.setHgrow(space, Priority.ALWAYS);
        this.getChildren().addAll(new Buttons(), space, new Infobar());
    }
}



