package augusta;

import augusta.tree.ProgNode;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import jdk.nashorn.internal.ir.Block;

/**
 * Created by Efe Ozturkoglu
 */
public class BlockControl extends Pane {
    public Label blockTypeLabel = new Label();
    private int pixelHeight = Theme.UI.BLOCK_UNIT_SIZE; // This equals unitHeight * Theme.UI.BLOCK_UNIT_SIZE
    private int unitHeight = 1; // Should be pretty small
    public BlockCategories blockCategory = BlockCategories.Command; // Default value

    public BlockControl() {
        super();
        blockTypeLabel.setLayoutX(Theme.UI.BLOCK_UNIT_SIZE / 4);
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
        this.unitHeight = val;
        this.pixelHeight = this.unitHeight * Theme.UI.BLOCK_UNIT_SIZE;
        this.setPrefHeight(pixelHeight);
        this.setPrefWidth(Theme.UI.BLOCK_WIDTH);
    }

    /**
     * Returns a ProgNode object of the control.
     * Should be overridden by each control.
     * @return
     */
    public ProgNode getProgNode() {
        return null;
    }

    public void recalculateSize() {
        unitHeight = 1;
        pixelHeight = unitHeight * Theme.UI.BLOCK_UNIT_SIZE;
    }


    public enum BlockCategories {
        Command,
        Loop,
        Conditional
    }

}
