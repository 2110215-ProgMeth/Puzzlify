package Utils;

import Block.BasicStructure.Block;
import Block.Buff.DoubleBlock;
import Block.Buff.HalfBlock;
import Block.Item.GabageBlock;
import Block.Item.JesusBlock;
import Block.Item.LaserBlock;
import Block.BasicStructure.NormalBlock;
import Block.Item.NuclearBlock;

public class Utils {
    public enum BlockType{
        J,L,O,S,T,Z,I,BOMB,LASER,X2,GARBAGE,JESUS,NULL,GAB,D2
    }
    public static Block RamdomBlock(double v1, double v2){
        int value = (int) (Math.random() * 1000);
        if(695<value&&value<=700){
            return new NuclearBlock(v1,v2);
        }
        else if(700<value&&value<=750){
            return new GabageBlock(v1,v2);
        }
        else if(750<value&&value<=800){
            return new HalfBlock(v1,v2);
        }
        else if(800<value&&value<=850){
            return new JesusBlock(v1,v2);
        }
        else if(850<value&&value<=900){
            return new DoubleBlock(v1,v2);
        }
        else if(900<value&&value<=950){
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
            case GARBAGE -> "BlockSprite/gab.png";
            case GAB-> "BlockSprite/garbege.png";
            case D2 -> "BlockSprite/d2.png";
            default -> "BlockSprite/yellow.png";
        };
    }
    public static String getFormSpritePath(BlockType bt){
        return switch (bt) {
            case I -> "BlockSprite/FormSprite/I.png";
            case J -> "BlockSprite/FormSprite/J.png";
            case O -> "BlockSprite/FormSprite/O.png";
            case S -> "BlockSprite/FormSprite/S.png";
            case Z -> "BlockSprite/FormSprite/Z.png";
            case T -> "BlockSprite/FormSprite/T.png";
            case L -> "BlockSprite/FormSprite/L.png";
            default -> "BlockSprite/FormSprite/J.png";
        };
    }

}
