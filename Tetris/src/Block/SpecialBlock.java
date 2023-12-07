package Block;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;

public abstract class SpecialBlock extends Block implements Skillable{
    static String imgPath; // have to edit this value in each special block
    static Image img = new Image(imgPath);
    public SpecialBlock(double v1, double v2) {
        super(v1, v2);
        setFill(new ImagePattern(img));
    }

    @Override
    public void activeSkill() {
        System.out.println("YO");
    }

    public abstract void activeSkill(Pane pane);
}
