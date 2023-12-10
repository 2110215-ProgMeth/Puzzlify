package Block.Item;

import Block.BasicStructure.SpecialBlock;
import Utils.Utils;
import javafx.scene.layout.Pane;

import static application.Tetris.*;

public class NuclearBlock extends SpecialBlock {
    private final int radius=2;
    public NuclearBlock(double v1, double v2) {
        super(v1, v2,"nuclear");
        bt = Utils.BlockType.BOMB;
    }

    @Override
    public void activeSkill(Pane pane) {
        System.out.println("I am Atomic!!!");
        clearGame(pane);
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
