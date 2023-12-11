package Block.Item;

import Block.BasicStructure.Block;
import Block.BasicStructure.Item;
import Block.BasicStructure.SpecialBlock;
import Utils.Utils;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;

import static application.Tetris.MESH;
import static application.Tetris.SIZE;


public class JesusBlock extends SpecialBlock implements Item {
    public JesusBlock(double v1,double v2){
        super(v1,v2,"jesus");
        bt = Utils.BlockType.JESUS;
    }

    @Override
    public void activeSkill(Pane pane) {
        sfx.play();
        System.out.println("Jesus!!");
        int x = (int) this.getX();
        ArrayList<Node> rects = new ArrayList<Node>();
        for (Node node : pane.getChildren()) {
            if (node instanceof Block)
                rects.add(node);
        }
        for (Node node : rects) {
            Block a = (Block) node;
            double ax = a.getX();
            if (ax == x) {
                MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
                pane.getChildren().remove(node);
            }
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
        rects.clear();
    }
}
