package com.mygame.boardgame.classes.Stage;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class RootContent extends Parent {

    BorderPane root =  new BorderPane();

    public void setRoot() {
        root.setPrefSize(650, 850);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        root.getChildren().add(null);
    }

    public BorderPane getRoot() {
        return root;
    }
}
