package augusta;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * Created by Efe Ozturkoglu
 */
public class BlockControl extends Pane {
    public Label blockTypeLabel = new Label();
    private int pixelHeight = 0; // This equals unitHeight * Theme.UI.BLOCK_UNIT_SIZE
    private int unitHeight = 0; // Should be pretty small

    public BlockControl() {
        super();
        blockTypeLabel.setLayoutX(Theme.UI.BLOCK_UNIT_SIZE);
        this.getChildren().add(blockTypeLabel);
        this.setMinWidth(Theme.UI.BLOCK_WIDTH);
        this.setMinHeight(Theme.UI.BLOCK_UNIT_SIZE);
    }

    public int getPixelHeight() {
        return pixelHeight;
    }
    public int getUnitHeight() {
        return unitHeight;
    }
    public void setUnitHeight(int val) {
        unitHeight = val;
        pixelHeight = unitHeight * Theme.UI.BLOCK_UNIT_SIZE;
        this.setPrefHeight(pixelHeight);
        this.setPrefWidth(Theme.UI.BLOCK_WIDTH);
    }
}
