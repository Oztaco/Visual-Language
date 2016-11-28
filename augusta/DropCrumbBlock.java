package augusta;

/**
 * Created by Efe Ozturkoglu
 */
public class DropCrumbBlock extends BlockControl {
    public DropCrumbBlock() {
        this.blockTypeLabel.setText("Drop Crumb");
        this.setStyle("-fx-background-color: " + Theme.Blocks.DROP_CRUMB);
    }
}
