package Special;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public abstract class SpecialRect extends Rectangle implements Skillable{
    static String imgPath;
    static Image img = new Image(imgPath);
    public SpecialRect(double v, double v1){
        super(v,v1);
        setFill(new ImagePattern(img));
    }
    @Override
    public void activeSkill() {
        System.out.println("YO");
    }
}
