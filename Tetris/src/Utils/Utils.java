package Utils;

import Block.*;

import application.Tetris;

public class Utils {
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
}
