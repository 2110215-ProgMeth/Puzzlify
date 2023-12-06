package Utils;

import Block.*;

import application.Tetris;

public class Utils {
    static Block randomType(){
        // 15 percent for special block
        int rand = (int)(Math.random() *100);
        if(rand <= 15){return new BombBlock(Tetris.SIZE-1,Tetris.SIZE-1);}
        return new NormalBlock(Tetris.SIZE-1,Tetris.SIZE-1);
    }
}
