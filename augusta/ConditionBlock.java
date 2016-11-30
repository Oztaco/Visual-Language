package augusta;

/**
 * Created by Efe Ozturkoglu
 */

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ConditionBlock extends BlockControl {
    public VBox childCommandsIf = new VBox();
    public VBox childCommandsElse = new VBox();
    public Label elseLabel = new Label();

    public ConditionBlock() {
        super();
        this.setUnitHeight(5);
        childCommandsIf.setLayoutY(Theme.UI.BLOCK_UNIT_SIZE);
        childCommandsIf.setLayoutX(Theme.UI.BLOCK_UNIT_SIZE);
        childCommandsIf.setPrefWidth(Theme.UI.BLOCK_WIDTH - Theme.UI.BLOCK_UNIT_SIZE);
        childCommandsIf.setMinHeight(Theme.UI.BLOCK_UNIT_SIZE);
        childCommandsIf.setStyle("-fx-background-color: " + Theme.UI.WORKSPACE_COLOR);

        childCommandsElse.setLayoutY(3 * Theme.UI.BLOCK_UNIT_SIZE);
        childCommandsElse.setLayoutX(Theme.UI.BLOCK_UNIT_SIZE);
        childCommandsElse.setPrefWidth(Theme.UI.BLOCK_WIDTH - Theme.UI.BLOCK_UNIT_SIZE);
        childCommandsElse.setMinHeight(Theme.UI.BLOCK_UNIT_SIZE);
        childCommandsElse.setStyle("-fx-background-color: " + Theme.UI.WORKSPACE_COLOR);

        this.elseLabel.setText("Else");
        this.elseLabel.setLayoutY(2 * Theme.UI.BLOCK_UNIT_SIZE);
        this.elseLabel.setLayoutX(Theme.UI.BLOCK_UNIT_SIZE);

        this.getChildren().add(childCommandsIf);
        this.getChildren().add(childCommandsElse);
        this.getChildren().add(elseLabel);
    }
}