package Block;

import application.Tetris;

public class DoubleBlock extends SpecialBlock{
    static String imgPath  = "Tetris/Resource/Double.jpg";
    public DoubleBlock(double v1,double v2){
        super(v1,v2);
    }

    @Override
    public void activeSkill() {
        System.out.println("Time to Double!");
    }
}
