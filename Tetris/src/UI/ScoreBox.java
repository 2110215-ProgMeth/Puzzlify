package UI;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ScoreBox implements Initializable {
    @FXML
    private Text scoreText;
    @FXML
    private Text labelText;
    ScaleTransition scaleTransition = new ScaleTransition();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scaleTransition.setNode(scoreText);
        scaleTransition.setDuration(Duration.millis(100));
        scaleTransition.setFromX(.8);
        scaleTransition.setToX(1.4);

        scaleTransition.setFromY(.8);
        scaleTransition.setToY(1.4);

        scaleTransition.setInterpolator(Interpolator.EASE_BOTH);
        scaleTransition.setAutoReverse(true);
    }
    public void setScore(int s){
        scoreText.setText(Integer.toString(Math.max(0,s)));

        scaleTransition.jumpTo(Duration.ZERO);
        scaleTransition.stop();

        scaleTransition.play();
    }
    public void setLableText(String s){
        labelText.setText(s);
    }
}
