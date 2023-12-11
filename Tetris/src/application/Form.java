package application;

import Block.BasicStructure.Block;
import Utils.Utils;

public class Form {
    private Block a;
    private Block b;
    private Block c;
    private Block d;
    private Utils.BlockType bt;
    private int form = 1;


    public Form(Block a, Block b, Block c, Block d, Utils.BlockType blockType) {//constructor with name
        setA(a);
        setB(b);
        setC(c);
        setD(d);


        setBt(blockType);

        getA().setBlockType(blockType);
        getB().setBlockType(blockType);
        getC().setBlockType(blockType);
        getD().setBlockType(blockType);


        getA().setColor();
        getB().setColor();
        getC().setColor();
        getD().setColor();
    }


    public Utils.BlockType getBlockType(){return bt;}


    public void changeForm() {//เป็นลูป 1->2->3->4->1
        if (form != 4) {
            form++;
        } else {
            form = 1;
        }
    }

    public static Form makeRect() {//สร้างobj
        int SIZE = Tetris.SIZE;
        int XMAX = Tetris.XMAX;
        int block = (int) (Math.random() * 100);
        Utils.BlockType bt= Utils.BlockType.NULL;
        // create function to random block
        // maybe change all Rectangle to Block
        Block a = Utils.RamdomBlock(SIZE-1, SIZE-1), b = Utils.RamdomBlock(SIZE-1, SIZE-1), c = Utils.RamdomBlock(SIZE-1, SIZE-1),
                d = Utils.RamdomBlock(SIZE-1, SIZE-1);
        if (block < 15) {
            a.setX(XMAX / 2 - SIZE);
            b.setX(XMAX / 2 - SIZE);
            b.setY(SIZE);
            c.setX(XMAX / 2);
            c.setY(SIZE);
            d.setX(XMAX / 2 + SIZE);
            d.setY(SIZE);
            bt = Utils.BlockType.J;
        } else if (block < 30) {
            a.setX(XMAX / 2 + SIZE);
            b.setX(XMAX / 2 - SIZE);
            b.setY(SIZE);
            c.setX(XMAX / 2);
            c.setY(SIZE);
            d.setX(XMAX / 2 + SIZE);
            d.setY(SIZE);
            bt = Utils.BlockType.L;
        } else if (block < 45) {
            a.setX(XMAX / 2 - SIZE);
            b.setX(XMAX / 2);
            c.setX(XMAX / 2 - SIZE);
            c.setY(SIZE);
            d.setX(XMAX / 2);
            d.setY(SIZE);
            bt = Utils.BlockType.O;
        } else if (block < 45) {
        } else if (block < 60) {
            a.setX(XMAX / 2 + SIZE);
            b.setX(XMAX / 2);
            c.setX(XMAX / 2);
            c.setY(SIZE);
            d.setX(XMAX / 2 - SIZE);
            d.setY(SIZE);
            bt = Utils.BlockType.S;
        } else if (block < 75) {
            a.setX(XMAX / 2 - SIZE);
            b.setX(XMAX / 2);
            c.setX(XMAX / 2);
            c.setY(SIZE);
            d.setX(XMAX / 2 + SIZE);
            bt = Utils.BlockType.T;
        } else if (block < 90) {
            a.setX(XMAX / 2 + SIZE);
            b.setX(XMAX / 2);
            c.setX(XMAX / 2 + SIZE);
            c.setY(SIZE);
            d.setX(XMAX / 2 + SIZE + SIZE);
            d.setY(SIZE);
            bt = Utils.BlockType.Z;
        } else {
            a.setX(XMAX / 2 - SIZE - SIZE);
            b.setX(XMAX / 2 - SIZE);
            c.setX(XMAX / 2);
            d.setX(XMAX / 2 + SIZE);
            bt = Utils.BlockType.I;
        }
//        System.out.println(a.getBlockType().toString());
        return new Form(a, b, c, d, bt);
    }

    public Block getA() {
        return a;
    }

    public void setA(Block a) {
        this.a = a;
    }

    public Block getB() {
        return b;
    }

    public void setB(Block b) {
        this.b = b;
    }

    public Block getC() {
        return c;
    }

    public void setC(Block c) {
        this.c = c;
    }

    public Block getD() {
        return d;
    }

    public void setD(Block d) {
        this.d = d;
    }

    public Utils.BlockType getBt() {
        return bt;
    }

    public void setBt(Utils.BlockType bt) {
        this.bt = bt;
    }
    public int getForm(){
        return form;
    }
}