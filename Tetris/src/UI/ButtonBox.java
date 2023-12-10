package UI;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ButtonBox implements Initializable {
    @FXML public ImageView imageView;

    private String enableButtonPath;
    private String disableButtonPath;
    private Image enableImg = new Image("/UISprite/StartButtonEnable.png");
    private Image disableImg = new Image("/UISprite/StartButtonDisable.png");
    private TranslateTransition translateTransition = new TranslateTransition();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageView.setImage(enableImg);
        translateTransition.setNode(imageView);
        translateTransition.setDuration(Duration.millis(1000));
        translateTransition.setByY(10);

        translateTransition.setInterpolator(Interpolator.EASE_BOTH);
        translateTransition.setCycleCount(-1);
        translateTransition.setAutoReverse(true);
        translateTransition.play();
    }

    public void mousePressed(){
        System.out.println("pressed");
        imageView.setImage(disableImg);
    }

    public void mouseReleased(){
        System.out.println("released");
        imageView.setImage(enableImg);
    }

    public void setEnableButtonPath(String s){
        enableButtonPath = s;
        enableImg = new Image(s);
    }
    public void setDisableButtonPath(String s){
        disableButtonPath = s;
        disableImg = new Image(s);
    }

}