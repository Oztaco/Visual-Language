package augusta;

/**
 * Created by Efe Ozturkoglu
 */

public class IfCrumbBlock extends ConditionBlock {
    public IfCrumbBlock() {
        super();
        this.setStyle("-fx-background-color: " + Theme.Blocks.IF_CRUMB);
        this.blockTypeLabel.setText("If Crumb?");
    }
}