package Block.Buff;

import Block.BasicStructure.Buff;
import Block.BasicStructure.SpecialBlock;
import Utils.Utils;
import javafx.scene.layout.Pane;
import Utils.Mode;

public class DoubleBlock extends SpecialBlock implements Buff {
    public DoubleBlock(double v1,double v2){
        super(v1,v2);
        bt = Utils.BlockType.X2;
    }

    @Override
    public void activeSkill(Pane pane) {
        Mode.activeDouble();
    }
}
