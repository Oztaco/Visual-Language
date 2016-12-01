package augusta;

import augusta.properties.Access;
import augusta.properties.Direction;
import augusta.tree.DoNothing;
import augusta.tree.IfBlocks;
import augusta.tree.IfCrumb;
import augusta.tree.ProgNode;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Efe Ozturkoglu
 */

public class IfBlockBlock extends ConditionBlock {
    private Access condition = Access.OPEN;
    private Direction direction = Direction.AHEAD;

    public IfBlockBlock() {
        super();
        this.setStyle("-fx-background-color: " + Theme.Blocks.IF_BLOCK);
        this.blockTypeLabel.setText("If (open, ahead)");

        this.blockTypeLabel.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getButton() == MouseButton.PRIMARY)
                            direction = CodeEditor.getIncrementedDirection(direction);
                        else if (event.getButton() == MouseButton.SECONDARY) {
                            // Inverts the condition
                            condition = (condition == Access.OPEN) ? Access.BLOCKED : Access.OPEN;
                        }
                        blockTypeLabel.setText("If (" + condition.toString().toLowerCase() + ", " + direction.toString().toLowerCase() + ")");
                    }
        });
    }

    @Override
    public ProgNode getProgNode() {
        List<ProgNode> ifcoms = new ArrayList<>();
        for (Node n : this.childCommandsIf.getChildren()) {
            BlockControl block = (BlockControl)n;
            ProgNode p = block.getProgNode();
            ifcoms.add(p);
        }
        if (ifcoms.size() < 1) ifcoms.add(new DoNothing());
        List<ProgNode> elsecoms = new ArrayList<>();
        for (Node n : this.childCommandsElse.getChildren()) {
            BlockControl block = (BlockControl)n;
            ProgNode p = block.getProgNode();
            elsecoms.add(p);
        }
        if (elsecoms.size() < 1) elsecoms.add(new DoNothing());
        IfBlocks ib = new IfBlocks(condition, direction, ifcoms, elsecoms);
        return ib;
    }
}