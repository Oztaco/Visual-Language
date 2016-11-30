package augusta;

/**
 * Created by Efe Ozturkoglu
 */

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
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

import static augusta.CodeEditor.commandsList;
import static augusta.CodeEditor.draggingItem;

public class LoopBlock extends BlockControl {
    public VBox childCommands = new VBox();

    public LoopBlock() {
        super();
        this.setUnitHeight(3);
        this.blockCategory = BlockCategories.Loop;
        childCommands.setLayoutY(Theme.UI.BLOCK_UNIT_SIZE);
        childCommands.setLayoutX(Theme.UI.BLOCK_UNIT_SIZE);
        childCommands.setPrefWidth(Theme.UI.BLOCK_WIDTH - Theme.UI.BLOCK_UNIT_SIZE);
        childCommands.setMinHeight(Theme.UI.BLOCK_UNIT_SIZE);
        childCommands.setStyle("-fx-background-color: " + Theme.UI.WORKSPACE_COLOR);
        this.getChildren().add(childCommands);
    }
    public void addCommand(int relativeHeight, BlockControl newCommand) {
        if (this.childCommands.getChildren().size() < 1) {
            this.childCommands.getChildren().add(newCommand);
        }
        else {
            boolean commandAdded = false;
            int accumulatedHeight = 0;
            for (int line = 0; line < this.childCommands.getChildren().size(); line++) {
                BlockControl command = (BlockControl)this.childCommands.getChildren().get(line);
                int heightOfCommand = accumulatedHeight;
                int bottomOfCommand = heightOfCommand + command.getPixelHeight();
                if (relativeHeight >= heightOfCommand && relativeHeight < bottomOfCommand) {
                    if (command.blockCategory == BlockControl.BlockCategories.Command) {
                        // If the block at that line is a "command" then add the new
                        // block after it
                        this.childCommands.getChildren().add(line, newCommand);
                        commandAdded = true;
                    }
                    else if (command.blockCategory == BlockControl.BlockCategories.Loop) {
                        // If the block at that line is a "loop" then add the new
                        // block into it
                        int newRelativeHeight = relativeHeight - heightOfCommand;
                        ((LoopBlock)this.childCommands.getChildren().get(line)).addCommand(newRelativeHeight, newCommand);
                        commandAdded = true;
                    }
                    else if (command.blockCategory == BlockControl.BlockCategories.Conditional) {
                        // If the block at that line is a "conditional" then add the nw
                        // block into one of the two
                        // TODO
                    }
                    break;
                }
                else {
                    accumulatedHeight += command.getPixelHeight();
                }
            }
            if (!commandAdded) {
                this.childCommands.getChildren().add(newCommand);
            }
        }

        this.setUnitHeight(this.getUnitHeight() + newCommand.getUnitHeight());
        int childrenSize = 0;
        for (Node n : this.childCommands.getChildren()) {
            BlockControl b = (BlockControl)n;
            childrenSize += b.getUnitHeight();
        }
        this.childCommands.setPrefHeight((childrenSize + 1) * Theme.UI.BLOCK_UNIT_SIZE);
    }
}