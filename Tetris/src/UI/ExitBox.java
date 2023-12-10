package UI;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ExitBox implements Initializable {
    @FXML private GridPane box;
    @FXML private Text ScoreTxt;
    @FXML private Text LineTxt;
    @FXML private ImageView restartBtn;
    @FXML private ImageView exitBtn;
    private AudioClip clickSound;
    private Image restartDis = new Image("/UISprite/ReStartdisable.png");
    private Image restartImg = new Image("/UISprite/ReStart.png");
    private Image exitDis = new Image("/UISprite/ExitDis.png");

    private AudioClip looseSFX;


    public ImageView getRestartBtn() {
        return restartBtn;
    }

    public GridPane getBox(){return box;}

    public void onRestartBtnPressed(){
        clickSound.play();restartBtn.setImage(restartDis);
    }
    public void onRestartBtnReleased(){
        restartBtn.setImage(restartImg);
    }
    public void onExitBtnPressed(){
        clickSound.play();
        exitBtn.setImage(exitDis);
        Platform.exit();
        System.exit(0);
    }

    public void onPaneActive(int score, int line){
        setLineTxt(line);
        setScoreTxt(score);
        box.setVisible(true);
        looseSFX.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        looseSFX = new AudioClip(getClass().getResource("/SFX/loose1.mp3").toExternalForm());
        looseSFX.setVolume(.2);
        clickSound = new AudioClip(getClass().getResource("/SFX/bui.wav").toExternalForm());
        clickSound.setVolume(.2);
        box.setVisible(false);
    }
    public void setScoreTxt(int score){
        ScoreTxt.setText("Score: "+ score);
    }
    public void setLineTxt(int line){
        LineTxt.setText("Line: "+ line);
    }
}
