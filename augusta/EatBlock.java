package augusta;

/**
 * Created by Efe Ozturkoglu
 */
public class EatBlock extends BlockControl {
    public EatBlock() {
        this.blockTypeLabel.setText("Eat");
        this.setStyle("-fx-background-color: " + Theme.Blocks.EAT);
    }
}
