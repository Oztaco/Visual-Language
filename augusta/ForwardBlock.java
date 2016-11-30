package augusta;

import javafx.scene.control.Label;

/**
 * Created by Efe Ozturkoglu
 */
public class ForwardBlock extends BlockControl {
    public ForwardBlock() {
        this.blockTypeLabel.setText("Forward");
        this.setStyle("-fx-background-color: " + Theme.Blocks.FORWARD);
    }
}
