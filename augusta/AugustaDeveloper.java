package augusta;

/**
 * Created by Efe Ozturkoglu
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AugustaDeveloper extends Application {

    private static final String TOP_BAR_COLOR = "-fx-background-color: #ff6666";
    private static final String PALETTE_COLOR = "-fx-background-color: #ff9999";
    private  static final String WORKSPACE_COLOR = "-fx-background-color: #ffffff";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Augusta Developer Studio");


        GridPane mainPanel = new GridPane();
            mainPanel.setStyle(WORKSPACE_COLOR);

            StackPane cell_0x0 = new StackPane(); // Top left cell with Save button
                Button saveButton = new Button();
                saveButton.setText("Save");
                cell_0x0.getChildren().add(saveButton);
                cell_0x0.setStyle(TOP_BAR_COLOR);
            GridPane.setConstraints(cell_0x0, 0, 0);

            StackPane cell_1x0 = new StackPane(); // Top bar, empty, just for appearance
                cell_1x0.setStyle(TOP_BAR_COLOR);
                cell_1x0.prefWidthProperty().bind(primaryStage.widthProperty());
                cell_1x0.prefHeightProperty().bind(saveButton.heightProperty());
                cell_1x0.setMaxWidth(450);
            GridPane.setConstraints(cell_1x0, 1, 0);

            VBox palette = new VBox(); // The Palette on the left
            palette.setStyle(PALETTE_COLOR);
            palette.setPrefWidth(150);
            palette.prefHeightProperty().bind(primaryStage.heightProperty());
                Button b = new Button();
                palette.getChildren().add(b);
            GridPane.setConstraints(palette, 0, 1);
        mainPanel.getChildren().addAll(cell_0x0, cell_1x0, palette);

        primaryStage.setScene(new Scene(mainPanel, 600, 400));
        primaryStage.show();
    }
}
