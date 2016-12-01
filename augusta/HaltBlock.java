package augusta;

import augusta.tree.Forward;
import augusta.tree.Halt;
import augusta.tree.ProgNode;

/**
 * Created by Efe Ozturkoglu
 */
public class HaltBlock extends BlockControl {
    public HaltBlock() {
        this.blockTypeLabel.setText("Halt!");
        this.setStyle("-fx-background-color: " + Theme.Blocks.HALT);
    }

    @Override
    public ProgNode getProgNode() {
        Halt h = new Halt();
        return h;
    }
}
