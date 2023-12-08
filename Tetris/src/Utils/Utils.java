package Utils;

import Block.BasicStructure.Block;
import Block.Buff.DoubleBlock;
import Block.Buff.HalfBlock;
import Block.Item.BombBlock;
import Block.Item.JesusBlock;
import Block.Item.LaserBlock;
import Block.BasicStructure.NormalBlock;

public class Utils {
    public static Block RamdomBlock(double v1, double v2){
        int value = (int) (Math.random() * 1000);
        if(750<value&&value<=800){
            return new HalfBlock(v1,v2);
        }
        else if(800<value&&value<=850){
            return new BombBlock(v1,v2);
        }
        else if(850<value&&value<=900){
            return new JesusBlock(v1,v2);
        }
        else if(900<value&&value<=950){
            return new DoubleBlock(v1,v2);
        }
        else if(950<value&&value<=1000){
            return new LaserBlock(v1,v2);
        }
        else{
            return new NormalBlock(v1,v2);
        }
    }
}
