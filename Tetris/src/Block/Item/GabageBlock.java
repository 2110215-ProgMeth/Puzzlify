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
        ArrayList<Node> newrects = new ArrayList<Node>();

                // get all node that recently from pane
                for (Node node : pane.getChildren()) {
                    if (node instanceof Block) {
                        Block a = (Block) node;
                        newrects.add(a);
                    }
                }
                for (Node node : newrects) {
                    Block a = (Block) node;
                    // node that higher than the line -> move down 1 block
                        MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
                        a.setY(a.getY() - SIZE);
                }
                newrects.clear();
                /// get all rectangle in pane
                for (Node node : pane.getChildren()) {
                    if (node instanceof Block)
                        newrects.add(node);
                }
                // บอกในMESHว่าตรงนี้มีBlock เพราะข้างบนลบออกแล้วยังไม่ได้เพิ่มใหม่
                for (Node node : newrects) {
                    Block a = (Block) node;
                    try {
                        MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 1;
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
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
