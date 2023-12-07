package Misc;

import javafx.scene.shape.Rectangle;

public class Wallpaper extends Rectangle {
    public Wallpaper(double v1, double v2){
        super(v1,v2);
        setStyle("-fx-fill: rgb(255, 246, 220); -fx-stroke: rgb(196, 193, 164); -fx-stroke-width: 1;");
    }

    public void setHightLight(boolean set){
        if(set){
            setStyle("-fx-fill: white; -fx-stroke: white; -fx-stroke-width: 1;");
        }
        else{
            setStyle("-fx-fill: rgb(255, 246, 220); -fx-stroke: rgb(196, 193, 164); -fx-stroke-width: 1;");
        }
    }

}
