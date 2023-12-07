package Block;

public class LaserBlock extends SpecialBlock {
    static String imgPath  = "Tetris/Resource/Laser.jpg";
    public LaserBlock(double v1,double v2){
        super(v1,v2);
    }

    @Override
    public void activeSkill() {
        System.out.println("Cut it All !!");
    }
}
