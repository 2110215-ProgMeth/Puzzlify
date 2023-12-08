package application;

import Block.BasicStructure.Block;
import Utils.Utils;

public class Form {
    Block a;
    Block b;
    Block c;
    Block d;
    private Utils.BlockType bt;
    public int form = 1;


    public Form(Block a, Block b, Block c, Block d, Utils.BlockType blockType) {//constructor with name
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;


        this.bt = blockType;

        this.a.setBlockType(blockType);
        this.b.setBlockType(blockType);
        this.c.setBlockType(blockType);
        this.d.setBlockType(blockType);


        this.a.setColor();
        this.b.setColor();
        this.c.setColor();
        this.d.setColor();
    }


    public Utils.BlockType getBlockType(){return bt;}


    public void changeForm() {//เป็นลูป 1->2->3->4->1
        if (form != 4) {
            form++;
        } else {
            form = 1;
        }
    }
}