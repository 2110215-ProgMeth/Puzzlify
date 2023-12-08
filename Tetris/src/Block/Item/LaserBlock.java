package Block.Item;

import Block.BasicStructure.Block;
import Block.BasicStructure.Item;
import Block.BasicStructure.SpecialBlock;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;

import static application.Tetris.MESH;
import static application.Tetris.SIZE;

public class LaserBlock extends SpecialBlock implements Item {
    private final static String imgPath  = "Laser.jpg";
    public LaserBlock(double v1,double v2){
        super(v1,v2);
        Image img = new Image(getimgPath());
        this.setFill(new ImagePattern(img));
    }
    public String getimgPath(){
        return imgPath;
    }

    @Override
    public void activeSkill(Pane pane) {
        ArrayList<Node> rects = new ArrayList<Node>();
        ArrayList<Integer> lines = new ArrayList<Integer>();
        ArrayList<Node> newrects = new ArrayList<Node>();
        lines.add(21);lines.add(22);lines.add(23);
        if (lines.size() > 0)
            do {
                for (Node node : pane.getChildren()) {
                    if (node instanceof Block)
                        rects.add(node);
                }
                for (Node node : rects) {
                    Block a = (Block) node;
                    if (a.getY() == lines.get(0) * SIZE) {
                        MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
                        pane.getChildren().remove(node);
                    } else
                        newrects.add(node);
                }
                for (Node node : newrects) {
                    Block a = (Block) node;
                    if (a.getY() < lines.get(0) * SIZE) {
                        MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
                        a.setY(a.getY() + SIZE);
                    }
                }
                lines.remove(0);
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
            } while (lines.size() > 0);
    }
}
