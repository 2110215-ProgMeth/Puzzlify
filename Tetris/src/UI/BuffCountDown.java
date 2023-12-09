package UI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class BuffCountDown implements Initializable {

    @FXML private ImageView countdownBar;
    @FXML private ImageView icon;
    private int max;
    private int curr;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

//    public void setMax(int s){
//        max = s;
//    }
//    public void setCurr(int c){
//        curr = c;
//    }
//
//    public void setIcon(Image img){
//        icon.setImage(img);
//    }
//
//    public void UpdateBar(int c){
//        countdownBar.setFitWidth((double) c /max);
//    }

}
