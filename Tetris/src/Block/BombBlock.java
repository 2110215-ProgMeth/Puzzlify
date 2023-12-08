package Block;

import Utils.Utils;
import application.Tetris;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import static application.Tetris.MESH;
import static application.Tetris.SIZE;

public class BombBlock extends SpecialBlock {
    private final int radius=2;
    public BombBlock(double v1, double v2) {
        super(v1, v2);
        bt = Utils.BlockType.BOMB;
    }

    @Override
    public void activeSkill(Pane pane) {
        System.out.println("bomb");
        int x = (int) this.getX();
        int y = (int) this.getY();
        ArrayList<Node> rects = new ArrayList<Node>();
        ArrayList<Node> newrects = new ArrayList<Node>();
        for (Node node : pane.getChildren()) {
            if (node instanceof Block)
                rects.add(node);
        }
        for (Node node : rects) {
            Block a = (Block) node;
            double ay = a.getY();
            double ax = a.getX();
            int r = radius*SIZE;
            if(ax<=x+r && ax>=x-r && ay<=y+r && ay>=y-r){
                System.out.println(ax + " " + ay);
                MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
                pane.getChildren().remove(node);
            } else
                newrects.add(node);
        }
        for (Node node : newrects) {
            Block a = (Block) node;
            if (a.getX()<=(x+2)*SIZE && (x-2)*SIZE<=a.getX() && a.getY()<=(y-2)*SIZE) {
                MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
                a.setY(a.getY() + 4*SIZE);//ติดนิดนึง
            }
        }
        rects.clear();
        newrects.clear();
        for (Node node : pane.getChildren()) {
            if (node instanceof Block)
                rects.add(node);
        }
        for (Node node : rects) {
            Block a = (Block) node;
            try {
                MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 1;
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }
        rects.clear();
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
