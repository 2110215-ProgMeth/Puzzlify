package UI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ScoreText implements Initializable {
    @FXML
    public Text scoreText;
    @FXML
    public Text labelText;
    private int score;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    public void setScore(int s){
        score = Math.max(0,s);
        scoreText.setText(Integer.toString(score));
    }

    public void addScore(int a){
        score += a;
        scoreText.setText(Integer.toString(score));
    }
}
