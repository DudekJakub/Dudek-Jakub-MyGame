import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import java.awt.*;
import java.util.ArrayList;

public class MyGame extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage mainStage) throws Exception {
        mainStage.setTitle("EscapeFromTheMaze");

        BorderPane root = new BorderPane();

        Scene mainScene = new Scene(root);
        mainStage.setScene(mainScene);

        //Code
        Canvas canvas = new Canvas(600, 600);
        GraphicsContext context = canvas.getGraphicsContext2D();
        root.setCenter(canvas);
        context.setFill(Color.BLACK);
        context.fillRect(0,0,600,600);

        ArrayList<String> inputList = new ArrayList<>();

        mainScene.setOnKeyPressed(
                (KeyEvent event) -> {
                    String keyName = event.getCode().toString();
                    if (!inputList.contains(keyName))
                        inputList.add(keyName);
                }
        );

        mainScene.setOnKeyReleased(
                (KeyEvent event) -> {
                    String keyName = event.getCode().toString();
                    inputList.remove(keyName);
                }
        );

        Sprite player = new Sprite();
        player.position.set(100,100);
        player.setImage("C:\\Users\\jakub\\IdeaProjects\\MyGame\\EscapeFromTheMaze\\src\\main\\resources\\playerRight.jpg");
        player.render(context);

        Items item1 = new Items(10, 10);
        item1.setImage("C:\\Users\\jakub\\IdeaProjects\\MyGame\\EscapeFromTheMaze\\src\\main\\resources\\player.png");
        item1.position.set(Math.random() * 300 + 100, Math.random() * 300 + 100);

        ArrayList<Items> collectItemList = new ArrayList<>();
        collectItemList.add(item1);

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long nanotime) {

            //playerSprite settings
            player.velocity.set(0,0);
                if (inputList.contains("LEFT"))
                    player.velocity.add(-50,0);
                if (inputList.contains("RIGHT"))
                    player.velocity.add(50,0);
                if (inputList.contains("UP"))
                    player.velocity.add(0, -50);
                if (inputList.contains("DOWN"))
                    player.velocity.add(0, 50);

                player.velocity.multiply(1/60.0);
                player.position.add(player.velocity);

                //background refresh
                context.setFill(Color.BLACK);
                context.fillRect(0,0,600,600);

                //check overlaps for this.item1
                if(player.overlapsItems(item1)) {
                    collectItemList.remove(item1);
                }

                //check overlaps for all items
                for (int i = 0; i < collectItemList.size(); i++) {

                    Items items = collectItemList.get(i);

                    if (player.overlapsItems(items))
                        collectItemList.remove(items);
                }

                //draw items
                for (Items item : collectItemList) {
                    item.render(context);
                }

                //draw player
                player.render(context);

            }
        };

        gameLoop.start();
        mainStage.show();
    }
}