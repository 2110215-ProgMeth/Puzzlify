package Block.BasicStructure;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.ImagePattern;

public abstract class SpecialBlock extends Block implements Skillable{
    protected AudioClip sfx;

   public SpecialBlock(double v1, double v2) {
        super(v1, v2);
   }


    @Override
    public abstract void activeSkill(Pane pane);
}
