package Block.Buff;

import Block.BasicStructure.Buff;
import Block.BasicStructure.SpecialBlock;
import Utils.Mode;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;

import static application.Tetris.scoreMode;

public class HalfBlock extends SpecialBlock implements Buff {
    private final static String imgPath  = "Half.jpg";
    public HalfBlock(double v1,double v2){
        super(v1,v2);
        Image img = new Image(getimgPath());
        this.setFill(new ImagePattern(img));
    }
    public String getimgPath(){
        return imgPath;
    }
    @Override
    public void activeSkill(Pane pane) {
        scoreMode = Mode.HALF;
        System.out.println("You got Debuff, Only get half score in next 15 seconds!");
        Thread thread = new Thread(()->{
            try{
                Thread.sleep(15000);
                scoreMode = Mode.DEFAULT;
                System.out.println("Debuff end!");
            }catch(InterruptedException e ){}
        });
        thread.start();
    }
}
