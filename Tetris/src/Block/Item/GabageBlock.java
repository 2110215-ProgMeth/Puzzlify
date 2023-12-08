package Block.Item;

import Block.BasicStructure.Block;
import Block.BasicStructure.Item;
import Block.BasicStructure.SpecialBlock;
import javafx.css.Size;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

import static application.Tetris.MESH;
import static application.Tetris.SIZE;

public class GabageBlock extends SpecialBlock implements Item {
    public GabageBlock(double v1, double v2){
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
        for (Node node : rects) {
            Block a = (Block) node;
            try {
                MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 1;
            } catch (ArrayIndexOutOfBoundsException e) {}
        }
        int emptyGrid = (int) (Math.random() * 11);
        for(int x=0;x!=12;x++){
            GabageBlock i = new GabageBlock(SIZE, SIZE);
            i.setX(x*SIZE);
            i.setY(23*SIZE);
            if(x!=emptyGrid) MESH[x][23]=1;
        }
        MESH[emptyGrid][23]=0;
    }
}
