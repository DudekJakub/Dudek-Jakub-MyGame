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

    ArrayList<Items> itemList1 = new ArrayList<>();
    ArrayList<Items> itemList2 = new ArrayList<>();
    ArrayList<Sprite> spriteList1 = new ArrayList<>();
    ArrayList<Sprite> spriteList2 = new ArrayList<>();

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


        Items item1 = new Items(10, 10);
        item1.setImage("C:\\Users\\jakub\\IdeaProjects\\MyGame\\EscapeFromTheMaze\\src\\main\\resources\\item1.png");
        item1.position.set(0, -200);

        Items item2 = new Items(5,5);
        item2.setImage("C:\\Users\\jakub\\IdeaProjects\\MyGame\\EscapeFromTheMaze\\src\\main\\resources\\item3.jpg");
        item2.position.set(300, -500);

        Items item3 = new Items(15,15);
        item3.setImage("C:\\Users\\jakub\\IdeaProjects\\MyGame\\EscapeFromTheMaze\\src\\main\\resources\\item4.jpg");
        item3.position.set(500, -700);

        Sprite player = new Sprite(10,10,100);
        player.position.set(canvas.getWidth()/2, 980);
        player.setImage("C:\\Users\\jakub\\IdeaProjects\\MyGame\\EscapeFromTheMaze\\src\\main\\resources\\playerRight.jpg");

        Sprite bullet = new Sprite(0,0,0);
        bullet.position.set(2000, 2000);
        bullet.setImage("C:\\Users\\jakub\\IdeaProjects\\MyGame\\EscapeFromTheMaze\\src\\main\\resources\\bullet1.png");

        Sprite boom = new Sprite(0,0,0);
        boom.position.set(2000, 2000);
        boom.setImage("C:\\Users\\jakub\\IdeaProjects\\MyGame\\EscapeFromTheMaze\\src\\main\\resources\\boom.gif");

        Sprite enemy = new Sprite(0,0,0);
        enemy.position.set(150, 0);
        enemy.setImage("C:\\Users\\jakub\\IdeaProjects\\MyGame\\EscapeFromTheMaze\\src\\main\\resources\\enemy.png");

        Sprite playerBlockerLEFT = new Sprite(0,0,0);
        playerBlockerLEFT.position.set(0, 980);
        playerBlockerLEFT.setImage("C:\\Users\\jakub\\IdeaProjects\\MyGame\\EscapeFromTheMaze\\src\\main\\resources\\PlayerMovementBlockerLEFT.png");

        Sprite playerBlockerRIGHT = new Sprite(0,0,0);
        playerBlockerRIGHT.position.set(canvas.getWidth(), 980);
        playerBlockerRIGHT.setImage("C:\\Users\\jakub\\IdeaProjects\\MyGame\\EscapeFromTheMaze\\src\\main\\resources\\PlayerMovementBlockerRIGHT.png");

        Text text = new Text("          ----------   STATYSTYKI   ----------                      ");
        Text text1 = new Text("         Intelekt:                                                         " + player.getIntel());
        Text text2 = new Text("         Siła:                                                         " + player.getStr());


        pane.setLeft(grid);
        grid.setGridLinesVisible(true);
        grid.add(text, 0,1);
        grid.add(text1, 0,2);
        grid.add(text2, 0,3);

        TimerExecutor timerExecutor = new TimerExecutor();

        //Lists ITEMS
        itemList1.add(item1);
        itemList1.add(item2);
        itemList1.add(item3);

        //Lists SPRITES
        spriteList1.add(enemy);


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
                item3.setVelocity(0, 50, item3);

                //collision & boom effect
                if (bullet.overlaps(enemy)) {
                         timerExecutor.boomEffect(2, boom, enemy);
                         spriteList1.remove(enemy);
                         spriteList2.add(enemy);
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
                for (Items addItemsToEQ : itemList1) {
                    if (player.overlapsItems(addItemsToEQ)) {
                        itemList1.stream()
                                .filter(e -> e.getBoundary().overlaps(player.getBoundary()))
                                .map(e -> player.getStr() + addItemsToEQ.getStr())
                                .map(e -> player.getIntel() + addItemsToEQ.getIntel())
                                .map(e -> itemList1.remove(addItemsToEQ))
                                .forEach(e -> itemList2.add(addItemsToEQ));
                    }
                }


                //logic for item's bonuses
                if (itemList2.contains(item1)) {
                    player.velocity.multiply(5);
                    player.position.add(player.velocity);
                }

                //clear the canvas
                context.setFill(Color.BLACK);
                context.fillRect(0,0, scene.getWidth()*0.5, canvas.getHeight());

                context1.setFill(Color.BURLYWOOD);
                context1.fillRect(0,0, scene.getWidth()*0.35, canvas1.getHeight());

                //draw sprites
                for (Sprite renderSprites : spriteList1) {
                    renderSprites.render(context);
                }

                //draw items
                for (Items renderItems : itemList1) {
                    renderItems.render(context);
                }

                //draw items + stream
                double positionY = 30;
                for (Items playerItems : itemList2) {
                    playerItems.render(context1);
                    itemList2.get(0).setPosition(0,0);
                    for (int n=0; n < itemList2.size(); n++) {
                        itemList2.get(0).setPosition(0,positionY);
                        positionY++;
                        playerItems.setPosition(0,positionY);
                    }
                }

                //draw elements
                player.render(context);
                playerBlockerLEFT.render(context);
                playerBlockerRIGHT.render(context);
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