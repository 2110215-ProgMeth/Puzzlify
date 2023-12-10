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
    private ScaleTransition scaleTransition = new ScaleTransition();
    private TranslateTransition translateTransitionLabel = new TranslateTransition();
    private TranslateTransition translateTransitionText = new TranslateTransition();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        translateTransitionLabel.setNode(labelText);
        translateTransitionLabel.setDuration(Duration.millis(1000));
        translateTransitionLabel.setByY(10);

        translateTransitionLabel.setInterpolator(Interpolator.EASE_BOTH);
        translateTransitionLabel.setCycleCount(-1);
        translateTransitionLabel.setAutoReverse(true);
        translateTransitionLabel.play();

        translateTransitionText.setNode(scoreText);
        translateTransitionText.setDuration(Duration.millis(1000));
        translateTransitionText.setByY(13);

        translateTransitionText.setInterpolator(Interpolator.EASE_BOTH);
        translateTransitionText.setCycleCount(-1);
        translateTransitionText.setAutoReverse(true);
        translateTransitionText.play();


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
