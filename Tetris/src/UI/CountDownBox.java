package UI;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class CountDownBox implements Initializable {
    @FXML
    public ImageView countDownImg;
    private Image img3 = new Image("/UISprite/CountDown/3.png");
    private Image img2 = new Image("/UISprite/CountDown/2.png");
    private Image img1 = new Image("/UISprite/CountDown/1.png");
    private Image trans = new Image("/BlockSprite/FormSprite/Transparent64x64.png");
    private ScaleTransition scaleTransition = new ScaleTransition();
    private AudioClip countClick;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countClick = new AudioClip(getClass().getResource("/SFX/bui.wav").toExternalForm());
        countClick.setVolume(.3);
        countDownImg = new ImageView(trans);
        scaleTransition.setNode(countDownImg);
        scaleTransition.setDuration(Duration.millis(400));
        scaleTransition.setFromX(0);
        scaleTransition.setFromY(0);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.setInterpolator(Interpolator.EASE_BOTH);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setCycleCount(2);
        scaleTransition.play();
    }

    public void countDown(int count){
        Image img = switch (count){
            case 3 -> img3;
            case 2 -> img2;
            case 1 -> img1;
            default -> trans;
        };
        scaleTransition.play();
        countClick.play();
        countDownImg.setImage(img);
    }

}
