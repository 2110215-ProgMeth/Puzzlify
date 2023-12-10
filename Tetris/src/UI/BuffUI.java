package UI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BuffUI extends ImageView {
    private Image buffActiveImg;
    private Image buffDeActiveImg;
    public BuffUI(String buffActiveImgPath, String buffDeActiveImgPath) {
        super(new Image(buffDeActiveImgPath));
        this.buffActiveImg = new Image(buffActiveImgPath);
        this.buffDeActiveImg = new Image(buffDeActiveImgPath);
    }
    public void isActive(boolean a){
        if(a){
            setImage(buffActiveImg);
        }
        else{
            setImage(buffDeActiveImg);
        }
    }



}
