package Block.Item;

import Block.BasicStructure.*;
import Utils.Utils;
import application.Tetris;
import javafx.css.Size;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

import static application.Tetris.MESH;
import static application.Tetris.SIZE;

public class GabageBlock extends SpecialBlock implements Item {
    public GabageBlock(double v1, double v2){
        super(v1,v2);
        bt = Utils.BlockType.GARBAGE;
    }

    @Override
    public void activeSkill(Pane pane) {
        System.out.println("Earthquake now ground be higher, try to remove it!");
        ArrayList<Node> rects = new ArrayList<Node>();
        for (Node node : pane.getChildren()) {
            if (node instanceof Block)
                rects.add(node);
        }
        for (Node node : rects) {
            Block a = (Block) node;
            MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
            a.setY(a.getY() - SIZE);
            MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 1;
        }
        int emptyGrid = (int) (Math.random() * 11);
        for(int x=0;x!=12;x++){
            if(x!=emptyGrid) {
                MESH[x][23]=1;
                GabBlock i = new GabBlock(SIZE, SIZE);
                i.setColor();
                i.setX(x*SIZE);
                i.setY(23*SIZE);
                Tetris.group.getChildren().add(i);
            }
        }
        MESH[emptyGrid][23]=0;
    }
}
