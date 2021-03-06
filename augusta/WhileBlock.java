package augusta;

import augusta.properties.Access;
import augusta.properties.Direction;
import augusta.tree.DoNothing;
import augusta.tree.ProgNode;
import augusta.tree.While;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Efe Ozturkoglu
 */

public class WhileBlock extends LoopBlock {
    private Access condition = Access.OPEN;
    private Direction direction = Direction.AHEAD;

    public WhileBlock() {
        super();
        this.setStyle("-fx-background-color: " + Theme.Blocks.WHILE);
        this.blockTypeLabel.setText("While (open, ahead)");

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
                        blockTypeLabel.setText("While (" + condition.toString().toLowerCase() + ", " + direction.toString().toLowerCase() + ")");
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
        While w = new While(condition, direction, coms);
        return w;
    }
}