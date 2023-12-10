package UI;

import application.Tetris;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class StartScene implements Initializable {
    @FXML private ImageView startBtn;
    @FXML private ImageView guideBtn;
    @FXML private ImageView exitBtn;
    @FXML private ImageView gameIcon;

    private AudioClip clickSound;

    private Image disStartBtnImg = new Image("/UISprite/StartButtonDisable.png");
    private Image startBtnImg = new Image("/UISprite/StartButtonEnable.png");
    private Image disGuideBtnImg = new Image("/UISprite/GuideBtnDisable.png");
    private Image guideBtnImg = new Image("/UISprite/GuideBtn.png");
    private Image disExitBtnImg = new Image("/UISprite/ExitBtnDisable.png");
    public TranslateTransition translateTransition = new TranslateTransition();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clickSound = new AudioClip(getClass().getResource("/SFX/bui.wav").toExternalForm());
        clickSound.setVolume(.2);
        translateTransition.setNode(gameIcon);
        translateTransition.setDuration(Duration.millis(2000));
        translateTransition.setByY(25);

        translateTransition.setInterpolator(Interpolator.EASE_BOTH);
        translateTransition.setCycleCount(-1);
        translateTransition.setAutoReverse(true);
        translateTransition.play();
    }

    public void OnStartBtnPressed(){
        startBtn.setImage(disStartBtnImg);
        clickSound.play();
    }
    public void OnStartBtnReleased(){
        startBtn.setImage(startBtnImg);
    }
    public void OnGuideBtnPressed(){
        guideBtn.setImage(disGuideBtnImg);
        clickSound.play();
    }
    public void OnGuidbtnReleased(){
        guideBtn.setImage(guideBtnImg);
    }
    public void OnExitBtnPressed(){
        exitBtn.setImage(disExitBtnImg);
        clickSound.play();
    }


    public ImageView getStartBtn() {
        return startBtn;
    }
    public ImageView getGuideBtn() {
        return guideBtn;
    }
    public ImageView getExitBtn(){return exitBtn;}

}
