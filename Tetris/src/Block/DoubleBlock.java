package Block;

import application.Tetris.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;

import static application.Tetris.DoubleNow;
import static application.Tetris.times;

public class DoubleBlock extends SpecialBlock{
    static String imgPath  = "Double.jpg";
    public DoubleBlock(double v1,double v2){
        super(v1,v2);
        Image img = new Image(imgPath);
        this.setFill(new ImagePattern(img));
    }

    @Override
    public void activeSkill(Pane pane) {
        DoubleNow = true;
        System.out.println("Time to Double!");
        Thread thread = new Thread(()->{
            try{
                Thread.sleep(15000);
                DoubleNow = false;
                System.out.println("Double end!");
            }catch(InterruptedException e ){}
        });
        thread.start();
    }
}
