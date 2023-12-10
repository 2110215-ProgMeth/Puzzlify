package UI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ExitBox implements Initializable {
    @FXML private Text ScoreTxt;
    @FXML private Text LineTxt;
    @FXML private ImageView restartBtn;
    @FXML private ImageView exitBtn;
    private AudioClip clickSound;
    private Image reStartDis = new Image("/UISprite/ReStartdisable.png");
    private Image restartImg = new Image("/UISprite/ReStart.png");
    private Image exitDis = new Image("/UISprite/ExitDis.png");


    public ImageView getRestartBtn() {
        return restartBtn;
    }

    public void OnRestartBtnPressed(){
        clickSound.play();restartBtn.setImage(reStartDis);
    }
    public void OnRestartBtnReleased(){
        restartBtn.setImage(restartImg);
    }
    public void OnExitBtnPressed(){
        clickSound.play();
        exitBtn.setImage(exitDis);
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clickSound = new AudioClip(getClass().getResource("/SFX/bui.wav").toExternalForm());
    }
    public void setScoreTxt(int score){
        ScoreTxt.setText("Score: "+ score);
    }
    public void setLineTxt(int line){
        LineTxt.setText("Line: "+ line);
    }
}
