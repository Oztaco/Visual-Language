package augusta;

/**
 * Created by Efe Ozturkoglu
 */

import augusta.tree.While;
import com.sun.org.apache.bcel.internal.classfile.Code;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import jdk.nashorn.internal.ir.Block;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static augusta.CodeEditor.commandsList;
import static augusta.CodeEditor.root;
import static augusta.Theme.*;
import static augusta.Theme.UI.PALETTE_COLOR;

public class AugustaDeveloper extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Augusta Developer Studio");

        root = new Pane();

        GridPane mainPanel = new GridPane();
        mainPanel.setStyle("-fx-background-color: #0000ff");
        mainPanel.prefWidthProperty().bind(primaryStage.widthProperty());
            //mainPanel.setStyle("-fx-background-color: " + Theme.UI.WORKSPACE_COLOR);
            mainPanel.minWidthProperty().bind(primaryStage.widthProperty());
            mainPanel.prefWidthProperty().bind(primaryStage.widthProperty());
            ColumnConstraints sideBarConstraints = new ColumnConstraints();
            sideBarConstraints.setPrefWidth(Theme.UI.BLOCK_WIDTH + 30);
            mainPanel.getColumnConstraints().add(sideBarConstraints);

            StackPane topBar = new StackPane(); // Top left cell with Save button
                GridPane.setColumnSpan(topBar, 2);
                Button saveButton = new Button();
                saveButton.setText("Save");
                topBar.getChildren().add(saveButton);
                topBar.setStyle("-fx-background-color: " + Theme.UI.TOP_BAR_COLOR);
            GridPane.setConstraints(topBar, 0, 0);

            VBox palette = new VBox(); // The Palette on the left
                palette.setStyle("-fx-background-color: " + Theme.UI.PALETTE_COLOR);
                palette.setPrefWidth(150);
                palette.prefHeightProperty().bind(primaryStage.heightProperty());
            GridPane.setConstraints(palette, 0, 1);

            VBox codeArea = new VBox();
                codeArea.setPadding(new Insets(15, 15, 15, 15));
            codeArea.setStyle("-fx-background-color: #eeffee");
                commandsList = new VBox();
                    commandsList.setStyle("-fx-background-color: #ffeeee");
                    commandsList.setFillWidth(true);
                    commandsList.prefHeightProperty().bind(primaryStage.heightProperty());
                codeArea.getChildren().add(commandsList);
            GridPane.setConstraints(codeArea, 1, 1);
        GridPane.setHgrow(codeArea, Priority.ALWAYS);

        mainPanel.getChildren().addAll(topBar, palette, codeArea);

        CodeEditor.root.getChildren().add(mainPanel);

        populatePalette(palette);

        // BEGIN DRAG
//        bus.addEventHandler(MouseEvent.MOUSE_PRESSED,
//                new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent event) {
//                        x = event.getSceneX();
//                        y = event.getSceneY();
//                        down = true;
//                    }
//                }
//        );
//        bus.addEventHandler(MouseEvent.MOUSE_RELEASED,
//                new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent event) {
//                        down = false;
//                    }
//                }
//        );
//        bus.addEventHandler(MouseEvent.ANY,
//                new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent event) {
//                        //if (down) {
//                            bus.setLayoutX(event.getSceneX() - 15);
//                            bus.setLayoutY(event.getSceneY() - 15);
//                            //System.out.println("Yep");
//                        //}
//                        //else
//                            //System.out.println("nope");
//                    }
//        });
//        // END DRAG





        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

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

        for (BlockControl blockControl : blocks) {
            blockControl.setMaxWidth(Theme.UI.BLOCK_WIDTH);

            blockControl.addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            BlockControl b, newBlock;
                            newBlock = new BlockControl();
                            try {
                                b = (BlockControl) event.getSource();
                                newBlock = b.getClass().getConstructor().newInstance();
                                CodeEditor.draggingItem = newBlock;
                                root.getChildren().add(newBlock);
                            } catch (Exception e) {
                                // cry
                            }
                            CodeEditor.beginDrag(newBlock, event);
                        }
                    }
            );
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
