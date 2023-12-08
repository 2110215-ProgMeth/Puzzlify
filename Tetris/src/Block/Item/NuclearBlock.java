package Block.Item;

import Block.BasicStructure.Block;
import Block.BasicStructure.SpecialBlock;
import Utils.Utils;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

import static application.Tetris.MESH;
import static application.Tetris.SIZE;

public class NuclearBlock extends SpecialBlock {
    private final int radius=2;
    public NuclearBlock(double v1, double v2) {
        super(v1, v2);
        bt = Utils.BlockType.BOMB;
    }

    @Override
    public void activeSkill(Pane pane) {
        System.out.println("I am Atomic!!!");
        ArrayList<Node> rects = new ArrayList<Node>();
        for (Node node : pane.getChildren()) {
            if (node instanceof Block)
                rects.add(node);
        }
        for (Node node : rects) {
            Block a = (Block) node;
            MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
            pane.getChildren().remove(node);
        }
        rects.clear();
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
