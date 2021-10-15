import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class TimerExecutor {

    public void boomEffect(double sec, Sprite sprite, Sprite sprite1) {
        sprite.position.set(sprite1.position.x - 100, sprite1.position.y - 100);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(sec), ae -> sprite.setPosition(2000, 2000)));
        timeline.play();
    }




}
