package augusta;

import augusta.tree.DoNothing;
import augusta.tree.IfCrumb;
import augusta.tree.ProgNode;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Efe Ozturkoglu
 */

public class IfCrumbBlock extends ConditionBlock {
    public IfCrumbBlock() {
        super();
        this.setStyle("-fx-background-color: " + Theme.Blocks.IF_CRUMB);
        this.blockTypeLabel.setText("If crumb?");
    }

    @Override
    public ProgNode getProgNode() {
        List<ProgNode> ifcoms = new ArrayList<>();
        for (Node n : this.childCommandsIf.getChildren()) {
            BlockControl block = (BlockControl)n;
            ProgNode p = block.getProgNode();
            ifcoms.add(p);
        }
        if (ifcoms.size() < 1) ifcoms.add(new DoNothing());
        List<ProgNode> elsecoms = new ArrayList<>();
        for (Node n : this.childCommandsElse.getChildren()) {
            BlockControl block = (BlockControl)n;
            ProgNode p = block.getProgNode();
            elsecoms.add(p);
        }
        if (elsecoms.size() < 1) elsecoms.add(new DoNothing());
        IfCrumb ic = new IfCrumb(ifcoms, elsecoms);
        return ic;
    }
}