package Block.Buff;

import Block.BasicStructure.Buff;
import Block.BasicStructure.SpecialBlock;
import Utils.Mode;
import Utils.Utils;
import Utils.sMode;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;

import static application.Tetris.scoreMode;

public class HalfBlock extends SpecialBlock implements Buff {
    public HalfBlock(double v1,double v2){
        super(v1,v2);
        bt = Utils.BlockType.D2;
    }
    @Override
    public void activeSkill(Pane pane) {
        Mode.activeHalf();
    }
}
