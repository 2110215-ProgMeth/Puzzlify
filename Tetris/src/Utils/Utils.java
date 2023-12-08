package Utils;

import Block.*;

import application.Tetris;

public class Utils {
    public enum BlockType{
        J,L,O,S,T,Z,I,BOMB,LASER,X2,GARBAGE,JESUS,NULL
    }
    public static Block RamdomBlock(double v1, double v2){
        int value = (int) (Math.random() * 1000);
        if(value>850&&900>value){
            return new BombBlock(v1,v2);
        }
        else if(value>900&&950>value){
            return  new DoubleBlock(v1,v2);
        }
        else if(value>950&&1000>value){
            return  new LaserBlock(v1,v2);
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
            case BOMB -> "BlockSprite/Bomb.png";
            case LASER -> "BlockSprite/laser.png";
            case JESUS -> "BlockSprite/Jesus.png";
            case X2 -> "BlockSprite/x2.png";
            case GARBAGE -> "BlockSprite/garbege.png";
            default -> "BlockSprite/yellow.png";
        };
    }

}
