package Block.Item;

import Block.BasicStructure.Block;
import Block.BasicStructure.SpecialBlock;
import Utils.Utils;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;

import java.util.ArrayList;

import static application.Tetris.*;

public class NuclearBlock extends SpecialBlock {
    private final int radius=2;
    public NuclearBlock(double v1, double v2) {
        super(v1, v2);
        bt = Utils.BlockType.BOMB;
        sfx = new AudioClip(this.getClass().getResource("/SFX/nuclear.wav").toExternalForm());
    }

    @Override
    public void activeSkill(Pane pane) {
        System.out.println("I am Atomic!!!");
        cleargame(pane);
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
