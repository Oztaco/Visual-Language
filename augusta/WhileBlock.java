package augusta;

/**
 * Created by Efe Ozturkoglu
 */

public class WhileBlock extends LoopBlock {
    public WhileBlock() {
        super();
        this.setStyle("-fx-background-color: " + Theme.Blocks.WHILE);
        this.blockTypeLabel.setText("While");
    }
}