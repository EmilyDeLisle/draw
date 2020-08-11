import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * GUI for the Draw application.
 * @author Emily DeLisle
 * @version 1.0
 */
public class Draw extends Application {

    /**
     * Launches the program.
     * @param primaryStage the primary Stage
     */
    @Override
    public void start(Stage primaryStage) {
        Surface surface = new Surface();
        BorderPane layout = new BorderPane(surface);
        Pen pen = new Pen(surface);
        Scene scene = new Scene(layout,1000, 780);
        Menu menu = new Menu(pen, scene);
        menu.setStyle("-fx-background-color: lightGrey;");
        layout.setTop(menu);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Draw");
        primaryStage.show();
    }
}
