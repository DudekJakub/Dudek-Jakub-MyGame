import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

public class TimerExecutor {

    public void boomEffect(double sec, Sprite sprite, Items item) {
        sprite.position.set(item.position.x - 100, item.position.y - 100);
        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.millis(sec), ae -> sprite.setPosition(2000, 2000)));
        timeline.play();
    }
}
