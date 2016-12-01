package augusta;

/**
 * Created by Efe Ozturkoglu
 */

import augusta.tree.DoNothing;
import augusta.tree.ProgNode;
import augusta.tree.Repeat;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
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

public class RepeatBlock extends LoopBlock {
    private int loopNumber = 2;

    public RepeatBlock() {
        super();
        this.setStyle("-fx-background-color: " + Theme.Blocks.REPEAT);
        this.blockTypeLabel.setText("Repeat (2)");

        this.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getButton() == MouseButton.PRIMARY)
                            loopNumber++;
                        else if (event.getButton() == MouseButton.SECONDARY)
                            loopNumber--;
                        if (loopNumber > 9) loopNumber = 1;
                        else if (loopNumber < 1) loopNumber = 9;
                        blockTypeLabel.setText("Repeat (" + loopNumber + ")");
                    }
                });
    }

    @Override
    public ProgNode getProgNode() {
        List<ProgNode> coms = new ArrayList<>();
        for (Node n : this.childCommands.getChildren()) {
            BlockControl block = (BlockControl)n;
            ProgNode p = block.getProgNode();
            coms.add(p);
        }
        if (coms.size() < 1) coms.add(new DoNothing());
        Repeat r = new Repeat(2, coms);
        return r;
    }
}