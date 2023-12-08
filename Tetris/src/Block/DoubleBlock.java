package Block;

import Utils.Utils;
import application.Tetris.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;

import static application.Tetris.DoubleNow;
import static application.Tetris.times;

public class DoubleBlock extends SpecialBlock{
    public DoubleBlock(double v1,double v2){
        super(v1,v2);
        bt = Utils.BlockType.X2;
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
