package augusta;

import augusta.properties.Direction;
import augusta.tree.ProgNode;
import augusta.tree.Turn;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Created by Efe Ozturkoglu
 */
public class TurnBlock extends BlockControl {
    private Direction direction = Direction.RIGHT;

    public TurnBlock() {
        this.blockTypeLabel.setText("Turn (right)");
        this.setStyle("-fx-background-color: " + Theme.Blocks.TURN);

        this.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getButton() == MouseButton.PRIMARY)
                            direction = CodeEditor.getIncrementedDirection(direction);
                        else if (event.getButton() == MouseButton.SECONDARY)
                            direction = CodeEditor.getDecrementedDirection(direction);
                        blockTypeLabel.setText("Turn (" + direction.toString().toLowerCase() + ")");
                    }
        });
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
