package augusta;

import augusta.tree.DoNothing;
import augusta.tree.Forward;
import augusta.tree.ProgNode;

/**
 * Created by Efe Ozturkoglu
 */
public class DoNothingBlock extends BlockControl {
    public DoNothingBlock() {
        this.blockTypeLabel.setText("Do nothing");
        this.setStyle("-fx-background-color: " + Theme.Blocks.DO_NOTHING);
    }

    @Override
    public ProgNode getProgNode() {
        DoNothing dn = new DoNothing();
        return dn;
    }
}
