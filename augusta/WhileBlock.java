package augusta;

import augusta.properties.Access;
import augusta.properties.Direction;
import augusta.tree.ProgNode;
import augusta.tree.While;
import javafx.scene.Node;

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
        this.blockTypeLabel.setText("While");
    }

    @Override
    public ProgNode getProgNode() {
        List<ProgNode> coms = new ArrayList<>();
        for (Node n : this.childCommands.getChildren()) {
            BlockControl block = (BlockControl)n;
            ProgNode p = block.getProgNode();
            coms.add(p);
        }
        While w = new While(condition, direction, coms);
        return w;
    }
}