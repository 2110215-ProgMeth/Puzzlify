package Block;

import application.Tetris.*;

import static application.Tetris.DoubleNow;
import static application.Tetris.times;

public class DoubleBlock extends SpecialBlock{
    static String imgPath  = "Tetris/Resource/Double.jpg";
    public DoubleBlock(double v1,double v2){
        super(v1,v2);
    }

    @Override
    public void activeSkill() {
        DoubleNow = true;
        System.out.println("Time to Double!");
        Thread thread = new Thread(()->{
            try{
                Thread.sleep(30000);
                DoubleNow = false;
            }catch(InterruptedException e ){}
        });
        thread.start();
    }
}
