package Block.BasicStructure;

import Block.BasicStructure.Block;
import Block.BasicStructure.Skillable;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;

public abstract class SpecialBlock extends Block implements Skillable {
    private final static String imgPath = "Nothing.jpg";
   public SpecialBlock(double v1, double v2) {
       super(v1, v2);
       Image img = new Image(getimgPath());
       this.setFill(new ImagePattern(img));
   }
   public String getimgPath(){
       return imgPath;
   }
    @Override
    public abstract void activeSkill(Pane pane);
}
