package UI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

import java.net.URL;
import java.util.ResourceBundle;

public class GuideScene implements Initializable {
    @FXML private ImageView backBtn;
    private Image backBtnImg = new Image("/UISprite/backBtn.png");
    private Image backBtnDisImg = new Image("/UISprite/backBtnDis.png");
    private AudioClip clickSound;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clickSound = new AudioClip(getClass().getResource("/SFX/bui.wav").toExternalForm());
        clickSound.setVolume(.2);
    }
    public ImageView getBackBtn(){return backBtn;}

    public void OnBackBtnPressed(){
        backBtn.setImage(backBtnDisImg);
        clickSound.play();
    }
    public void OnBackBtnReleased(){
        backBtn.setImage(backBtnImg);
    }
}
