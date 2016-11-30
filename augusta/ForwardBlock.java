package augusta;

import augusta.tree.Forward;
import augusta.tree.ProgNode;
import javafx.scene.control.Label;

/**
 * Created by Efe Ozturkoglu
 */
public class ForwardBlock extends BlockControl {
    public ForwardBlock() {
        this.blockTypeLabel.setText("Forward");
        this.setStyle("-fx-background-color: " + Theme.Blocks.FORWARD);
    }

    @Override
    public ProgNode getProgNode() {
        Forward f = new Forward();
        return f;
    }
}
