package Block.BasicStructure;

import Utils.Utils;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public abstract class Block extends Rectangle {
    protected Utils.BlockType bt = Utils.BlockType.NULL;
    public Block(double v1, double v2){
        super(v1,v2);
    }

    public void setBlockType(Utils.BlockType bt) {
        if(this.bt == Utils.BlockType.NULL){this.bt = bt;}
    }

    public void setColor() {
        Image img = new Image(Utils.getBlockSpritePath(this.bt));
        this.setFill(new ImagePattern(img));
    }
}
