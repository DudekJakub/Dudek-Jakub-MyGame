import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class MyGame extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("EscapeFromTheSpace");

        BorderPane pane = new BorderPane();

        Scene scene = new Scene(pane, 1920, 1080, Color.BEIGE);
        stage.setScene(scene);

        //Canvas & Grid
        Canvas canvas = new Canvas(scene.getWidth()*0.5, scene.getHeight());
        GraphicsContext context = canvas.getGraphicsContext2D();
        pane.setCenter(canvas);

        Canvas canvas1 = new Canvas(scene.getWidth()*0.35, scene.getHeight());
        GraphicsContext context1 = canvas1.getGraphicsContext2D();
        pane.setRight(canvas1);


        GridPane grid = new GridPane();
        double colNum = 1;
        double rowNum = 20;
        for(int i=0; i < colNum; i++){
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPrefWidth(600.0);
            colConst.setPercentWidth(100/colNum);
            grid.getColumnConstraints().add(colConst);
        }
        for(int i=0; i < rowNum; i++){
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100/rowNum);
            grid.getRowConstraints().add(rowConst);
        }

        Text text = new Text("          a                                                          ");
        Text text1 = new Text("         b                                                         ");
        Text text2 = new Text("         c                                                         ");
        Text text3 = new Text("         d                                                         ");
        Text text4 = new Text("         e                                                         ");
        Text text5 = new Text("         f                                                         ");

        pane.setLeft(grid);
        grid.setGridLinesVisible(true);
        grid.add(text, 0,1);
        grid.add(text1, 0,2);
        grid.add(text2, 0,3);
        grid.add(text3, 0,4);
        grid.add(text4, 0,5);
        grid.add(text5, 0,6);


        ArrayList<String> inputList = new ArrayList<>();

        scene.setOnKeyPressed(
                (KeyEvent event) -> {
                    String keyName = event.getCode().toString();
                    if (!inputList.contains(keyName))
                        inputList.add(keyName);
                }
        );

        scene.setOnKeyReleased(
                (KeyEvent event) -> {
                    String keyName = event.getCode().toString();
                    inputList.remove(keyName);
                }
        );

        Sprite player = new Sprite();
        player.position.set(canvas.getWidth()/2, 980);
        player.setImage("C:\\Users\\jakub\\IdeaProjects\\MyGame\\EscapeFromTheMaze\\src\\main\\resources\\playerRight.jpg");

        Items item1 = new Items(10, 10);
        item1.setImage("C:\\Users\\jakub\\IdeaProjects\\MyGame\\EscapeFromTheMaze\\src\\main\\resources\\item1.png");
        item1.position.set(0, -200);

        Items item2 = new Items(5,5);
        item2.setImage("C:\\Users\\jakub\\IdeaProjects\\MyGame\\EscapeFromTheMaze\\src\\main\\resources\\item2.png");
        item2.position.set(300, -500);

        Sprite bullet = new Sprite();
        bullet.position.set(2000, 2000);
        bullet.setImage("C:\\Users\\jakub\\IdeaProjects\\MyGame\\EscapeFromTheMaze\\src\\main\\resources\\bullet1.png");

        Sprite boom = new Sprite();
        boom.position.set(2000, 2000);
        boom.setImage("C:\\Users\\jakub\\IdeaProjects\\MyGame\\EscapeFromTheMaze\\src\\main\\resources\\boom.gif");

        Sprite enemy = new Sprite();
        enemy.position.set(150, 0);
        enemy.setImage("C:\\Users\\jakub\\IdeaProjects\\MyGame\\EscapeFromTheMaze\\src\\main\\resources\\enemy.png");

        Sprite playerBlockerLEFT = new Sprite();
        playerBlockerLEFT.position.set(0, 980);
        playerBlockerLEFT.setImage("C:\\Users\\jakub\\IdeaProjects\\MyGame\\EscapeFromTheMaze\\src\\main\\resources\\PlayerMovementBlockerLEFT.png");

        Sprite playerBlockerRIGHT = new Sprite();
        playerBlockerRIGHT.position.set(canvas.getWidth(), 980);
        playerBlockerRIGHT.setImage("C:\\Users\\jakub\\IdeaProjects\\MyGame\\EscapeFromTheMaze\\src\\main\\resources\\PlayerMovementBlockerRIGHT.png");

        TimerExecutor timerExecutor = new TimerExecutor();

        //Lists
        ArrayList<Items> LIST1 = new ArrayList<>();
        LIST1.add(item1);
        LIST1.add(item2);

        ArrayList<Items> LIST2 = new ArrayList<>();

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {

                //playerSprite settings
                player.velocity.set(0, 0);
                double speed = 100;

                if (inputList.contains("LEFT")) {
                    player.velocity.add(-speed, 0);
                }
                if (inputList.contains("RIGHT")) {
                    player.velocity.add(speed, 0);
                }
                if (inputList.contains("SPACE")) {
                    bullet.position.set(player.getPosition().x + 30, player.getPosition().y - 40);
                }

                //bullet velocity
                bullet.setVelocity(0, -100, bullet);

                //enemy velocity
                enemy.setVelocity(0, 50, enemy);

                //items velocity
                item1.setVelocity(0, 50, item1);
                item2.setVelocity(0, 50, item2);


                //collision & boom effect
                if (bullet.overlapsItems(item1)) {
                         timerExecutor.boomEffect(7000, boom, item1);
                         LIST1.remove(item1);
                         LIST2.add(item1);
                         bullet.position.set(2000, 2000);
                }

                //player velocity & player movement restrictions
                player.velocity.multiply(1/60.0);
                player.position.add(player.velocity);

                //blocker (to avoid moveing out of screen)
                if(player.overlaps(playerBlockerLEFT)) {
                    player.setPosition(playerBlockerLEFT.getPosition().x + 10, 980);
                } else if (player.overlaps(playerBlockerRIGHT)) {
                    player.setPosition(playerBlockerRIGHT.getPosition().x - 70, 980);
                }

                //loop for items
                for (Items addToEQItems : LIST1) {
                    if (player.overlapsItems(addToEQItems)) {
                        LIST1.stream()
                                .filter(e -> e.getBoundary().overlaps(player.getBoundary()))
                                .map(e -> LIST1.remove(addToEQItems))
                                .forEach(e -> LIST2.add(addToEQItems));
                    }
                }

                //logic for item's bonuses
                if (LIST2.contains(item1)) {
                    player.velocity.multiply(5);
                    player.position.add(player.velocity);
                }

                //clear the canvas
                context.setFill(Color.BLACK);
                context.fillRect(0,0, scene.getWidth()*0.5, canvas.getHeight());

                context1.setFill(Color.BURLYWOOD);
                context1.fillRect(0,0, scene.getWidth()*0.35, canvas1.getHeight());

                //draw items
                for (Items renderItems : LIST1) {
                    renderItems.render(context);
                }

                for (Items playerItems : LIST2) {
                    playerItems.render(context1);
                    LIST2.stream()
                            .map(e -> LIST2.get(0).position.x == 0)
                            .map(e -> LIST2.get(0).position.y == 0)
                            .map(e -> LIST2.get(0).getBoundary().y)
                            .forEach(e -> playerItems.setPosition(0, 30));
                }

                //draw elements
                player.render(context);
                playerBlockerLEFT.render(context);
                playerBlockerRIGHT.render(context);
                enemy.render(context);
                boom.render(context);
                bullet.render(context);
            }
        };
        gameLoop.start();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}