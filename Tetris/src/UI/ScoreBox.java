package UI;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
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
    TranslateTransition translateTransition = new TranslateTransition();
    TranslateTransition translateTransitionS = new TranslateTransition();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        translateTransition.setNode(labelText);
        translateTransition.setDuration(Duration.millis(1000));
        translateTransition.setByY(10);

        translateTransition.setInterpolator(Interpolator.EASE_BOTH);
        translateTransition.setCycleCount(-1);
        translateTransition.setAutoReverse(true);
        translateTransition.play();

        translateTransitionS.setNode(scoreText);
        translateTransitionS.setDuration(Duration.millis(1000));
        translateTransitionS.setByY(13);

        translateTransitionS.setInterpolator(Interpolator.EASE_BOTH);
        translateTransitionS.setCycleCount(-1);
        translateTransitionS.setAutoReverse(true);
        translateTransitionS.play();


        scaleTransition.setNode(scoreText);
        scaleTransition.setDuration(Duration.millis(100));
        scaleTransition.setFromX(.5);
        scaleTransition.setToX(1);

        scaleTransition.setFromY(.5);
        scaleTransition.setToY(1);

        scaleTransition.setInterpolator(Interpolator.EASE_BOTH);
        scaleTransition.setAutoReverse(true);
    }
    public void setScore(int s){
        scoreText.setText(Integer.toString(Math.max(0,s)));

        scaleTransition.jumpTo(Duration.ZERO);
        scaleTransition.stop();

        scaleTransition.play();
    }
    public void setLabelText(String s){
        labelText.setText(s);
    }
}
