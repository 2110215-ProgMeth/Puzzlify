package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import Block.*;

public class Form {
    Block a;
    Block b;
    Block c;
    Block d;
    Color color;
    private String name;
    public int form = 1;

    public Form(Block a, Block b, Block c, Block d) {//default constructor
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public Form(Block a, Block b, Block c, Block d, String name) {//constructor with name
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.name = name;

        switch (name) {//ลงสีรูปร่างต่างๆ
            case "j":
                color = Color.SLATEGRAY;
                break;
            case "l":
                color = Color.DARKGOLDENROD;
                break;
            case "o":
                color = Color.INDIANRED;
                break;
            case "s":
                color = Color.FORESTGREEN;
                break;
            case "t":
                color = Color.CADETBLUE;
                break;
            case "z":
                color = Color.HOTPINK;
                break;
            case "i":
                color = Color.SANDYBROWN;
                break;

        }
        //เพิ่มสีให้กับแต่ละBlock
        if(a instanceof Skillable){}else{this.a.setFill(color);}
        if(b instanceof Skillable){}else{this.b.setFill(color);}
        if(c instanceof Skillable){}else{this.c.setFill(color);}
        if(d instanceof Skillable){}else{this.d.setFill(color);}
    }


    public String getName() {//getter
        return name;
    }
    //น่าจะเอาไปทำอะไรอย่างอื่น เช่น MoveTurn() ต้องรูปเป็นรูปอะไรถึงจะสามารถหมุนอย่างถูกต้องได้


    public void changeForm() {//เป็นลูป 1->2->3->4->1
        if (form != 4) {
            form++;
        } else {
            form = 1;
        }
    }
}