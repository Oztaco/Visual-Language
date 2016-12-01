package augusta;

/**
 * Created by Efe Ozturkoglu
 * This file is the "View" in MVC
 */

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static augusta.CodeEditor.commandsList;
import static augusta.CodeEditor.root;

public class AugustaDeveloper extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Sets up the layout of the window
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Augusta Developer Studio");
        root = new Pane();

        GridPane mainPanel = new GridPane(); // Divides the window into a grid
        mainPanel.setStyle("-fx-background-color: #0000ff");
        mainPanel.prefWidthProperty().bind(primaryStage.widthProperty());
            //mainPanel.setStyle("-fx-background-color: " + Theme.UI.WORKSPACE_COLOR);
            mainPanel.minWidthProperty().bind(primaryStage.widthProperty());
            mainPanel.prefWidthProperty().bind(primaryStage.widthProperty());
            ColumnConstraints sideBarConstraints = new ColumnConstraints();
            sideBarConstraints.setPrefWidth(Theme.UI.BLOCK_WIDTH + 30);
            mainPanel.getColumnConstraints().add(sideBarConstraints);

            StackPane topBar = new StackPane(); // The bar at the top with the save button
                GridPane.setColumnSpan(topBar, 2);
                Button saveButton = new Button();
                saveButton.setText("Save");
                saveButton.setStyle("-fx-base: " + Theme.UI.BUTTON_BASE);
                saveButton.setPrefWidth(60);
                saveButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        CodeEditor.saveProgram(primaryStage);
                    }
                });
                topBar.getChildren().add(saveButton);
                topBar.setStyle("-fx-background-color: " + Theme.UI.TOP_BAR_COLOR);
                topBar.setAlignment(Pos.CENTER_LEFT);
                topBar.setPadding(new Insets(0, 0, 0, ((Theme.UI.BLOCK_WIDTH + 30) / 2) - (saveButton.getPrefWidth() / 2)));
            GridPane.setConstraints(topBar, 0, 0);

            VBox palette = new VBox(); // The Palette of Blocks on the left
                palette.setStyle("-fx-background-color: " + Theme.UI.PALETTE_COLOR);
                palette.setPrefWidth(150);
                palette.prefHeightProperty().bind(primaryStage.heightProperty());
            GridPane.setConstraints(palette, 0, 1);

            VBox codeArea = new VBox(); // The section that houses the Blocks of the program
                codeArea.setPadding(new Insets(15, 15, 15, 15));
            codeArea.setStyle("-fx-background-color: " + Theme.UI.WORKSPACE_COLOR);
                commandsList = new VBox();
                    commandsList.setFillWidth(true);
                    commandsList.prefHeightProperty().bind(primaryStage.heightProperty());
                codeArea.getChildren().add(commandsList);
            GridPane.setConstraints(codeArea, 1, 1);
        GridPane.setHgrow(codeArea, Priority.ALWAYS);

        mainPanel.getChildren().addAll(topBar, palette, codeArea);

        CodeEditor.root.getChildren().add(mainPanel);
        // Fills the Palette with sample controls for the user to
        // click and drag
        populatePalette(palette);

        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);
        primaryStage.setMaximized(true);
        primaryStage.setScene(new Scene(root, 850, 650));
        primaryStage.show();
    }

    /**
     * Creates instances of Blocks and adds them to the Palette
     * and adds event handlers to begin the drag and drop process
     * @param palette the user control to populate
     */
    public void populatePalette(VBox palette) {
        List<BlockControl> blocks = new ArrayList<>();

        ForwardBlock forward = new ForwardBlock();
        blocks.add(forward);

        TurnBlock turn = new TurnBlock();
        blocks.add(turn);

        DropCrumbBlock dropCrumb = new DropCrumbBlock();
        blocks.add(dropCrumb);

        EatBlock eat = new EatBlock();
        blocks.add(eat);

        RepeatBlock repeat = new RepeatBlock();
        blocks.add(repeat);

        WhileBlock whileBlock = new WhileBlock();
        blocks.add(whileBlock);

        IfCrumbBlock ifCrumb = new IfCrumbBlock();
        blocks.add(ifCrumb);

        IfBlockBlock ifBlock = new IfBlockBlock();
        blocks.add(ifBlock);

        DoNothingBlock doNothingBlock = new DoNothingBlock();
        blocks.add(doNothingBlock);

        HaltBlock haltBlock = new HaltBlock();
        blocks.add(haltBlock);

        for (BlockControl blockControl : blocks) {
            blockControl.setMaxWidth(Theme.UI.BLOCK_WIDTH);
            // This event handler will trigger the drag and drop functionality
            blockControl.addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            BlockControl b, newBlock;
                            newBlock = new BlockControl();
                            try {
                                // Create a new instance of whatever block the user selects
                                b = (BlockControl) event.getSource();
                                newBlock = b.getClass().getConstructor().newInstance(); // Requires exception handling
                                CodeEditor.draggingItem = newBlock;
                                root.getChildren().add(newBlock);
                            } catch (Exception e) {
                                // cry
                                //
                                // This catch block will never be called. However, the line of code in
                                // the "try" block above that says "requires exception handling" next to it
                                // requires that we catch many errors. However, none of those errors should
                                // actually **ever** occur during execution. That is the only reason
                                // that we are catching all exceptions like this
                                System.out.println(e.getMessage());
                            }
                            newBlock.makeDraggable(); // Allows the user to move blocks later
                            CodeEditor.beginDrag(newBlock, event);
                        }
                    }
            );
            // Checks for mouse movements to update the blocks location
            blockControl.addEventHandler(MouseEvent.MOUSE_MOVED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            CodeEditor.updateDrag(event);
                        }
                    }
            );

            palette.getChildren().add(blockControl);
        }
        root.addEventHandler(MouseEvent.ANY,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        CodeEditor.updateDrag(event);
                    }
                }
        );
        palette.setPadding(new Insets(15, 15, 15, 15));
    }
}
