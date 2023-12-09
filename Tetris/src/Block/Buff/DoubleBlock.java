package Block.Buff;

import Block.BasicStructure.Buff;
import Block.BasicStructure.SpecialBlock;
import Utils.Utils;
import javafx.scene.layout.Pane;
import Utils.Mode;
import javafx.scene.media.AudioClip;

public class DoubleBlock extends SpecialBlock implements Buff {
    public DoubleBlock(double v1,double v2){
        super(v1,v2);
        bt = Utils.BlockType.X2;
        sfx = new AudioClip(this.getClass().getResource("/SFX/x2.wav").toExternalForm());
    }

    @Override
    public void activeSkill(Pane pane) {
        sfx.play();
        Mode.activeDouble();
    }
}
