package augusta;

/**
 * Created by Efe Ozturkoglu
 */
public class TurnBlock extends BlockControl {
    public TurnBlock() {
        this.blockTypeLabel.setText("Turn");
        this.setStyle("-fx-background-color: " + Theme.Blocks.TURN);
    }
}
