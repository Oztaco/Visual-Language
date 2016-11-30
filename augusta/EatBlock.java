package augusta;

import augusta.tree.Eat;
import augusta.tree.ProgNode;

/**
 * Created by Efe Ozturkoglu
 */
public class EatBlock extends BlockControl {
    public EatBlock() {
        this.blockTypeLabel.setText("Eat");
        this.setStyle("-fx-background-color: " + Theme.Blocks.EAT);
    }

    @Override
    public ProgNode getProgNode() {
        Eat p = new Eat();
        return p;
    }
}
