package augusta;

/**
 * Created by Efe Ozturkoglu
 */

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static augusta.Theme.*;
import static augusta.Theme.UI.PALETTE_COLOR;

public class AugustaDeveloper extends Application {

    private double x = 0;
    private double y = 0;
    private boolean down = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Augusta Developer Studio");

        Pane root = new Pane();

        GridPane mainPanel = new GridPane();
        mainPanel.prefWidthProperty().bind(primaryStage.widthProperty());
            mainPanel.setStyle("-fx-background-color: " + Theme.UI.WORKSPACE_COLOR);

            StackPane cell_0x0 = new StackPane(); // Top left cell with Save button
                Button saveButton = new Button();
                saveButton.setText("Save");
                cell_0x0.getChildren().add(saveButton);
                cell_0x0.setStyle("-fx-background-color: " + Theme.UI.TOP_BAR_COLOR);
            GridPane.setConstraints(cell_0x0, 0, 0);

            StackPane cell_1x0 = new StackPane(); // Top bar, empty, just for appearance
                cell_1x0.setStyle("-fx-background-color: " + Theme.UI.TOP_BAR_COLOR);
                cell_1x0.prefWidthProperty().bind(primaryStage.widthProperty());
                cell_1x0.prefHeightProperty().bind(saveButton.heightProperty());
                cell_1x0.setMaxWidth(450);
            GridPane.setConstraints(cell_1x0, 1, 0);

            VBox palette = new VBox(); // The Palette on the left
            palette.setStyle("-fx-background-color: " + Theme.UI.PALETTE_COLOR);
            palette.setPrefWidth(150);
            palette.prefHeightProperty().bind(primaryStage.heightProperty());
                Button b = new Button();
                palette.getChildren().add(b);
            GridPane.setConstraints(palette, 0, 1);
        mainPanel.getChildren().addAll(cell_0x0, cell_1x0, palette);

        root.getChildren().add(mainPanel);
        RepeatBlock bus = new RepeatBlock();
        bus.setLayoutX(200);
        bus.setLayoutY(76);

        // BEGIN DRAG
        bus.addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        x = event.getSceneX();
                        y = event.getSceneY();
                        down = true;
                    }
                }
        );
        bus.addEventHandler(MouseEvent.MOUSE_RELEASED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        down = false;
                    }
                }
        );
        bus.addEventHandler(MouseEvent.ANY,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        //if (down) {
                            bus.setLayoutX(event.getSceneX() - 15);
                            bus.setLayoutY(event.getSceneY() - 15);
                            //System.out.println("Yep");
                        //}
                        //else
                            //System.out.println("nope");
                    }
        });
        // END DRAG



        root.getChildren().add(bus);

        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }
}
