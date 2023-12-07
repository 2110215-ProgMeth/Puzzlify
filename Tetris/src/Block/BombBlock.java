package Block;

import application.Tetris;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import static application.Tetris.MESH;
import static application.Tetris.SIZE;

public class BombBlock extends SpecialBlock {

//    static String imgPath = "Tetris/Resource/havel-photo.jpg";
    private final int radius=2;
    public BombBlock(double v1, double v2) {
        super(v1/2, v2/2);
    }

//    @Override
//    public void activeSkill(Pane pane) {
//        int x = (int) this.getX();
//        int y = (int) this.getY();
//        int r = radius*SIZE;
//        for (Node node : pane.getChildren()) {
//            if(node instanceof Rectangle){
//                Rectangle a = (Rectangle) node;
//                double ay = a.getY();
//                double ax = a.getX();
////                if ((a.getY()<=y +radius*SIZE && (y-radius)*SIZE<=a.getY()) && (a.getX()<=(x+radius)*SIZE && (x-radius)*SIZE<=a.getX())) {
//                if(ax<=x+r && ax>=x-r && ay<=y+r && ay>=y-r){
//                    System.out.println(ax + " " + ay);
//                    Tetris.removeBlock(a);
//                }
//            }
//        }
//    }

    @Override
    public void activeSkill(Pane pane) {
        System.out.println("bomb");
        int x = (int) this.getX();
        int y = (int) this.getY();
        ArrayList<Node> rects = new ArrayList<Node>();
        ArrayList<Node> newrects = new ArrayList<Node>();
        for (Node node : pane.getChildren()) {
            if (node instanceof Rectangle)
                rects.add(node);
        }
        for (Node node : rects) {
            Rectangle a = (Rectangle) node;
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
            Rectangle a = (Rectangle) node;
            if (a.getX()<=(x+2)*SIZE && (x-2)*SIZE<=a.getX() && a.getY()<=(y-2)*SIZE) {
                MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
                a.setY(a.getY() + 4*SIZE);//ติดนิดนึง
            }
        }
        rects.clear();
        newrects.clear();
        for (Node node : pane.getChildren()) {
            if (node instanceof Rectangle)
                rects.add(node);
        }
        for (Node node : rects) {
            Rectangle a = (Rectangle) node;
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
