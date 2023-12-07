package Block;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import static application.Tetris.MESH;
import static application.Tetris.SIZE;

public class BombBlock extends SpecialBlock {

    static String imgPath = "Tetris/Resource/havel-photo.jpg";
    public BombBlock(double v1, double v2) {
        super(v1, v2);
    }

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
            if ((a.getY()<=y+2 && y-2<=a.getY()) || (a.getX()<=x+2 && x-2<=a.getX())) {
                MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
                pane.getChildren().remove(node);
            } else
                newrects.add(node);
            }
                for (Node node : newrects) {
                    Rectangle a = (Rectangle) node;
                    if (a.getX()<=x+2 && x-2<=a.getX() && a.getY()<=y-2) {
                        MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
                        a.setY(a.getY() + 4*SIZE);
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
}
