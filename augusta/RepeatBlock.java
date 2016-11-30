package augusta;

/**
 * Created by Efe Ozturkoglu
 */

import augusta.tree.ProgNode;
import augusta.tree.Repeat;
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

public class RepeatBlock extends LoopBlock {
    public RepeatBlock() {
        super();
        this.setStyle("-fx-background-color: " + Theme.Blocks.REPEAT);
        this.blockTypeLabel.setText("Repeat");
    }

    @Override
    public ProgNode getProgNode() {
        List<ProgNode> coms = new ArrayList<>();
        for (Node n : this.childCommands.getChildren()) {
            BlockControl block = (BlockControl)n;
            ProgNode p = block.getProgNode();
            coms.add(p);
        }
        Repeat r = new Repeat(2, coms);
        return r;
    }
}