package augusta;

import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Created by Efe Ozturkoglu
 * This class handles drag and drop stuff. It's part of the "Controller" of MVC
 */
public class CodeEditor {

    public static Pane root; // The root of the GUI
    public static VBox commandsList; // The user drags blocks from the palette to this control
    public static BlockControl draggingItem;

    public static double offsetX = 0;
    public static double offsetY = 0;
    public static boolean isDragging = false;

    public static void beginDrag(BlockControl b, MouseEvent e) {
        if (isDragging) return;
        offsetX = e.getX();
        offsetY = e.getY();
        draggingItem = b;
        isDragging = true;
        updateDrag(e);
    }
    public static void endDrag(MouseEvent e) {
        if (!isDragging) return;
        isDragging = false;
        Bounds b = commandsList.localToScene(commandsList.getBoundsInLocal());
        if (e.getX() < b.getMinX() ||
                e.getY() < b.getMinY()) { // User dropped control in palette or topBar
            // Do nothing, the code at the bottom of this method will delete the block
        }
        else { // The user dropped control in code area
            int lineOfDragged = (int)((e.getY() - b.getMinY()) / Theme.UI.BLOCK_UNIT_SIZE);
            int heightOfDraggedCommand = (int)(e.getY() - b.getMinY());
            System.out.println(lineOfDragged);
            if (commandsList.getChildren().size() < 1) {
                commandsList.getChildren().add(draggingItem);
            }
            else {
                boolean commandAdded = false;
                int accumulatedHeight = 0;
                for (int line = 0; line < commandsList.getChildren().size(); line++) {
                    BlockControl command = (BlockControl)commandsList.getChildren().get(line);
                    int heightOfCommand = accumulatedHeight;
                    int bottomOfCommand = heightOfCommand + command.getPixelHeight();
                    if (heightOfDraggedCommand >= heightOfCommand && heightOfDraggedCommand < bottomOfCommand) {
                        if (command.blockCategory == BlockControl.BlockCategories.Command) {
                            // If the block at that line is a "command" then add the new
                            // block after it
                            commandsList.getChildren().add(line, draggingItem);
                            commandAdded = true;
                        }
                        else if (command.blockCategory == BlockControl.BlockCategories.Loop) {
                            // If the block at that line is a "loop" then add the new
                            // block into it
                            int relativeHeight = heightOfDraggedCommand - heightOfCommand;
                            ((LoopBlock)commandsList.getChildren().get(line)).addCommand(relativeHeight, draggingItem);
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
                    commandsList.getChildren().add(draggingItem);
                }
            }
        }
        root.getChildren().remove(draggingItem);
        draggingItem = null;
    }
    public static void updateDrag(MouseEvent e) {
        if (!isDragging || draggingItem == null) return;
        if (e.isPrimaryButtonDown()) {
            draggingItem.setLayoutX(e.getSceneX() - offsetX);
            draggingItem.setLayoutY(e.getSceneY() - offsetY);
        }
        else
            endDrag(e);
    }
}
