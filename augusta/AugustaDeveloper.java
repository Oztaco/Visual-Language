package augusta;

/**
 * Created by Efe Ozturkoglu
 */

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AugustaDeveloper extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Augusta Developer Studio");

        GridPane mainPanel = new GridPane();
            Button saveButton = new Button();


        primaryStage.show();
    }
}
