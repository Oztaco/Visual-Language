package augusta;

import augusta.tree.Drop;
import augusta.tree.ProgNode;

/**
 * Created by Efe Ozturkoglu
 */
public class DropCrumbBlock extends BlockControl {
    public DropCrumbBlock() {
        this.blockTypeLabel.setText("Drop crumb");
        this.setStyle("-fx-background-color: " + Theme.Blocks.DROP_CRUMB);
    }

    @Override
    public ProgNode getProgNode() {
        Drop d = new Drop();
        return d;
    }
}
