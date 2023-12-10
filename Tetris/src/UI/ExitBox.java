package UI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ExitBox implements Initializable {
    @FXML public Text ScoreTxt;
    @FXML public Text LineTxt;
    @FXML private ImageView restartBtn;
    @FXML private ImageView exitBtn;
    private Image reStartDis = new Image("/UISprite/ReStartdisable.png");
    private Image restartImg = new Image("/UISprite/ReStart.png");
    private Image exitDis = new Image("/UISprite/ExitDis.png");


    public ImageView getRestartBtn() {
        return restartBtn;
    }

    public void OnRestartBtnPressed(){
        restartBtn.setImage(reStartDis);
    }
    public void OnRestartBtnReleased(){
        restartBtn.setImage(restartImg);
    }
    public void OnExitBtnPressed(){
        exitBtn.setImage(exitDis);
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    public void setScoreTxt(int score){
        ScoreTxt.setText("Score: "+ score);
    }
    public void setLineTxt(int line){
        ScoreTxt.setText("Line: "+ line);
    }
}
