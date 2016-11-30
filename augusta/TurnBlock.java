package augusta;

import augusta.properties.Direction;
import augusta.tree.ProgNode;
import augusta.tree.Turn;

/**
 * Created by Efe Ozturkoglu
 */
public class TurnBlock extends BlockControl {
    private Direction direction = Direction.AHEAD;

    public TurnBlock() {
        this.blockTypeLabel.setText("Turn");
        this.setStyle("-fx-background-color: " + Theme.Blocks.TURN);
    }
    public Direction getDirection() {
        return direction;
    }

    @Override
    public ProgNode getProgNode() {
        Turn t = new Turn(direction);
        return t;
    }
}
