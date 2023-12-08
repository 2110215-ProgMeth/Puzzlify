package Block.BasicStructure;

import Utils.Utils.BlockType;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class NormalBlock extends Block{
    public NormalBlock(double v1, double v2) {
        super(v1, v2);
        bt = BlockType.NULL;
    }

}
