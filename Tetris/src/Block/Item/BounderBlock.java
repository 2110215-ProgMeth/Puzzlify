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

public class BounderBlock extends SpecialBlock implements Item {
    public  BounderBlock(double v1, double v2){
        super(v1,v2);
    }

    @Override
    public void activeSkill(Pane pane) {
        System.out.println("Earthquake now ground be higher, try to remove it!");
        ArrayList<Node> rects = new ArrayList<Node>();
        ArrayList<Integer> lines = new ArrayList<Integer>();
        for (Node node : pane.getChildren()) {
            if (node instanceof Block)
                rects.add(node);
        }
        for (Node node : rects) {
            Block a = (Block) node;
            MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
            a.setY(a.getY() - SIZE);
        }
        rects.clear();
        for (Node node : pane.getChildren()) {
            if (node instanceof Block)
                rects.add(node);
        }
        for (Node node : rects) {
            Block a = (Block) node;
            try {
                MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 1;
            } catch (ArrayIndexOutOfBoundsException e) {}
        }
    }
}
