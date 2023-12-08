package Utils;

import Block.BasicStructure.Block;
import Block.Buff.DoubleBlock;
import Block.Buff.HalfBlock;
import Block.Item.BombBlock;
import Block.Item.BounderBlock;
import Block.Item.JesusBlock;
import Block.Item.LaserBlock;
import Block.BasicStructure.NormalBlock;

public class Utils {
    public enum BlockType{
        J,L,O,S,T,Z,I,BOMB,LASER,X2,GARBAGE,JESUS,NULL
    }
    public static Block RamdomBlock(double v1, double v2){
        int value = (int) (Math.random() * 1000);
        /*if(700<value&&value<=750){
            return new BounderBlock(v1,v2);
        }
        else*/ if(750<value&&value<=800){
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

    public static String getBlockSpritePath(BlockType bt){
        return switch (bt) {
            case I -> "BlockSprite/blue.png";
            case J -> "BlockSprite/yellow.png";
            case O -> "BlockSprite/green.png";
            case S -> "BlockSprite/red.png";
            case Z -> "BlockSprite/white.png";
            case T -> "BlockSprite/orange.png";
            case L -> "BlockSprite/purple.png";
            case BOMB -> "BlockSprite/nuclear.png";
            case LASER -> "BlockSprite/laser.png";
            case JESUS -> "BlockSprite/Jesus.png";
            case X2 -> "BlockSprite/x2.png";
            case GARBAGE -> "BlockSprite/garbege.png";
            default -> "BlockSprite/yellow.png";
        };
    }

}
