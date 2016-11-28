package augusta;

/**
 * Created by efeec on 11/28/2016.
 */

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jdk.nashorn.internal.ir.Block;

import java.util.ArrayList;
import java.util.List;

public class RepeatBlock extends BlockControl {
    public VBox childCommands = new VBox();

    public RepeatBlock() {
        super();
        this.setStyle("-fx-background-color: " + Theme.Blocks.REPEAT);
        this.setUnitHeight(3);
        childCommands.setLayoutY(Theme.UI.BLOCK_UNIT_SIZE);
        childCommands.setLayoutX(Theme.UI.BLOCK_UNIT_SIZE);
        childCommands.setPrefWidth(Theme.UI.BLOCK_WIDTH - Theme.UI.BLOCK_UNIT_SIZE);
        childCommands.setMinHeight(Theme.UI.BLOCK_UNIT_SIZE);
        childCommands.setStyle("-fx-background-color: " + Theme.UI.WORKSPACE_COLOR);
        this.getChildren().add(childCommands);
        this.blockTypeLabel.setText("Repeat");
    }
}
