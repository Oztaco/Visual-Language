package augusta;

/**
 * Created by Efe Ozturkoglu
 */

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import jdk.nashorn.internal.ir.Block;

/**
 * Provides functionality specific to condition blocks.
 * Should be overridden by any Block that has conditions (not loops)
 */
public class ConditionBlock extends BlockControl {
    public VBox childCommandsIf = new VBox();
    public VBox childCommandsElse = new VBox();
    public Label elseLabel = new Label();
    private int unitHeight = 5;

    /**
     * Overridden constructor to add condition block specific
     * UI features
     */
    public ConditionBlock() {
        super();
        this.setUnitHeight(5);
        this.blockCategory = BlockCategories.Conditional;
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
        this.elseLabel.setLayoutX(Theme.UI.BLOCK_UNIT_SIZE / 4);

        this.getChildren().add(childCommandsIf);
        this.getChildren().add(childCommandsElse);
        this.getChildren().add(elseLabel);
    }

    /**
     * Called to add Blocks as children of this Block
     * @param relativeHeight used to see which command comes before each other
     * @param newCommand the new command to add
     */
    public void addCommand(int relativeHeight, BlockControl newCommand) {
        boolean addToTopBlock = true; // If false, add to bottom block (the "else" block)
        if (relativeHeight > this.childCommandsIf.getLayoutY() + this.childCommandsIf.getHeight())
            addToTopBlock = false;
        if (addToTopBlock)
            addCommandHelper(relativeHeight, newCommand, childCommandsIf);
        else
            addCommandHelper(relativeHeight - (int)(this.childCommandsIf.getLayoutY() + this.childCommandsIf.getHeight()), newCommand, childCommandsElse);
        recalculateSize();
    }

    /**
     * Helper method to do all of the heavy lifting for the addCommand method
     * @param relativeHeight
     * @param newCommand
     * @param childCommands
     */
    public void addCommandHelper(int relativeHeight, BlockControl newCommand, VBox childCommands) {
        if (childCommands.getChildren().size() < 1) {
            childCommands.getChildren().add(newCommand);
        }
        else {
            boolean commandAdded = false;
            int accumulatedHeight = 0;
            for (int line = 0; line < childCommands.getChildren().size(); line++) {
                BlockControl command = (BlockControl)childCommands.getChildren().get(line);
                int heightOfCommand = accumulatedHeight;
                int bottomOfCommand = heightOfCommand + command.getPixelHeight();
                if (relativeHeight >= heightOfCommand && relativeHeight < bottomOfCommand) {
                    if (command.blockCategory == BlockControl.BlockCategories.Command) {
                        // If the block at that line is a "command" then add the new
                        // block after it
                        childCommands.getChildren().add(line, newCommand);
                        commandAdded = true;
                    }
                    else if (command.blockCategory == BlockControl.BlockCategories.Loop) {
                        // If the block at that line is a "loop" then add the new
                        // block into it
                        int newRelativeHeight = relativeHeight - heightOfCommand;
                        ((LoopBlock)childCommands.getChildren().get(line)).addCommand(newRelativeHeight, newCommand);
                        commandAdded = true;
                    }
                    else if (command.blockCategory == BlockControl.BlockCategories.Conditional) {
                        // If the block at that line is a "conditional" then add the nw
                        // block into one of the two
                        int newRelativeHeight = relativeHeight - heightOfCommand;
                        ((ConditionBlock)childCommands.getChildren().get(line)).addCommand(newRelativeHeight, newCommand);
                        commandAdded = true;
                    }
                    break;
                }
                else {
                    accumulatedHeight += command.getPixelHeight();
                }
            }
            if (!commandAdded) {
                childCommands.getChildren().add(newCommand);
            }
        }
        int childrenSize = 0;
        for (Node n : childCommands.getChildren()) {
            BlockControl b = (BlockControl)n;
            childrenSize += b.getUnitHeight();
        }
        childCommands.setPrefHeight((childrenSize + 1) * Theme.UI.BLOCK_UNIT_SIZE);
    }

    /**
     * Overridden to accommodate the different sizing requirements of condition blocks
     */
    @Override
    public void recalculateSize() {
        int newUnitHeight = 1;
        for (Node n : childCommandsIf.getChildren()) {
            BlockControl b = (BlockControl) n;
            b.recalculateSize();
            newUnitHeight += b.getUnitHeight();
        }
        childCommandsIf.setPrefHeight(newUnitHeight * Theme.UI.BLOCK_UNIT_SIZE);
        newUnitHeight++;
        elseLabel.setLayoutY(newUnitHeight * Theme.UI.BLOCK_UNIT_SIZE);
        newUnitHeight += 1; // For the "else" block
        childCommandsElse.setLayoutY(newUnitHeight * Theme.UI.BLOCK_UNIT_SIZE);
        int elseBlockHeight = 0;
        for (Node n : childCommandsElse.getChildren()) {
            BlockControl b = (BlockControl) n;
            b.recalculateSize();
            newUnitHeight += b.getUnitHeight();
            elseBlockHeight += b.getPixelHeight();
        }
        childCommandsElse.setPrefHeight(elseBlockHeight + Theme.UI.BLOCK_UNIT_SIZE);
        newUnitHeight += 2; // For the piece at the bottom of the block
        this.setUnitHeight(newUnitHeight);
    }

    @Override
    public void setUnitHeight(int val) {
        super.setUnitHeight(val);
    }
}