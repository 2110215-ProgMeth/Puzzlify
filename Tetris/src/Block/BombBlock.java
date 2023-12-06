package Block;

public class BombBlock extends SpecialBlock {

    static String imgPath = "Tetris/Resource/havel-photo.jpg";
    public BombBlock(double v1, double v2) {
        super(v1, v2);
    }

    @Override
    public void activeSkill() {
        System.out.println("bomb");
    }
}
