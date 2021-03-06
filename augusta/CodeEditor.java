package augusta;

import augusta.properties.Direction;
import augusta.tree.ProgNode;
import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import jdk.nashorn.internal.ir.Block;

import java.io.*;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Called when a user clicks on any control that has a direction component.
     * Increments the direction clockwise
     * @param old The current direction
     * @return The next direction
     */
    public static Direction getIncrementedDirection(Direction old) {
        if (old == Direction.AHEAD) return Direction.RIGHT;
        else if (old == Direction.RIGHT) return Direction.BEHIND;
        else if (old == Direction.BEHIND) return Direction.LEFT;
        else return Direction.AHEAD;
    }

    /**
     * Decrements the direction, i.e. gets the next counterclockwise direction
     * @param old The current direction
     * @return The next direction
     */
    public static Direction getDecrementedDirection(Direction old) {
        if (old == Direction.AHEAD) return Direction.LEFT;
        else if (old == Direction.RIGHT) return Direction.AHEAD;
        else if (old == Direction.BEHIND) return Direction.RIGHT;
        else return Direction.BEHIND;
    }

    /**
     * Recalculates the height of each block based on its children
     */
    public static void refreshSizes() {
        for (Node n : commandsList.getChildren()) {
            BlockControl b = (BlockControl) n;
            b.recalculateSize();
        }
    }

    /**
     * Begins the drag and drop operation
     * @param b The block to drag
     * @param e The event information from the system
     */
    public static void beginDrag(BlockControl b, MouseEvent e) {
        if (isDragging) return;
        offsetX = e.getX();
        offsetY = e.getY();
        draggingItem = b;
        isDragging = true;
        updateDrag(e);
    }

    /**
     * Ends the drag and drop operation. If the mouse is over the
     * workspace, the control will be added to the list of commands.
     * Otherwise, it'll be deleted.
     * @param e Mouse events passed by the system
     */
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
            if (commandsList.getChildren().size() < 1) { // If there are no other commands
                commandsList.getChildren().add(draggingItem);
            }
            // If there are other commands, we must do some math to figure out
            // if the block we're dragging is going to added inside another block
            // or before/after it.
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
                            int relativeHeight = heightOfDraggedCommand - heightOfCommand;
                            ((ConditionBlock)commandsList.getChildren().get(line)).addCommand(relativeHeight, draggingItem);
                            commandAdded = true;
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
        refreshSizes();
    }

    /**
     * Called every time the mouse is moved to update the Block's position
     * and perform the drag and drop operation.
     * @param e Mouse event information passed by the system.
     */
    public static void updateDrag(MouseEvent e) {
        if (!isDragging || draggingItem == null) return;
        if (e.isPrimaryButtonDown()) {
            draggingItem.setLayoutX(e.getSceneX() - offsetX);
            draggingItem.setLayoutY(e.getSceneY() - offsetY);
        }
        else
            endDrag(e);
    }

    /**
     * Gets all of the Blocks, converts them into progNodes using
     * block.getProgNode(). It then serializes them and saves to a
     * file that the user chooses.
     * @param owner Required parameter to display dialog window.
     */
    public static void saveProgram(Window owner) {
        Alert alert = new Alert(Alert.AlertType.ERROR,
                "The file could not be saved for an unknown reason.",
                ButtonType.OK);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose save location");
        fileChooser.setInitialFileName("MyProgram.aug");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Augusta Programs (*.aug)", "*.aug"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All files", "*.*"));

        File file = fileChooser.showSaveDialog(owner);
        if (file != null) {
            List<ProgNode> prog = compileProgram();
            try (ObjectOutputStream oos =
                         new ObjectOutputStream(
                                 new FileOutputStream(file.getAbsolutePath()))) {
                for (ProgNode p : prog) {
                    oos.writeObject(p);
                }
            }
            catch (AccessDeniedException e) {
                alert.setContentText("Error: You do not have the required permissions to write to that file.");
                alert.showAndWait();
            }
            catch (IOException e) {
                alert.setContentText("There was an IO Exception while writing to the file: \n\n" + e.getMessage());
                alert.showAndWait();
            }
        }
        else {
            // Do nothing, the user did not choose a file
        }
    }

    /**
     * Helper function for the saveProgram() method. Gets the progNode
     * version of each Block.
     * @return a list of ProgNode objects based on user input
     */
    public static List<ProgNode> compileProgram() {
        List<ProgNode> prog = new ArrayList<>();
        for (Node n : commandsList.getChildren()) {
            BlockControl block = (BlockControl) n;
            prog.add(block.getProgNode());
        }
        return prog;
    }
}
