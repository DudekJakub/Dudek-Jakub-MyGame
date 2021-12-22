package com.mygame.boardGameEngine.classes.scene;

import javafx.scene.Parent;
import javafx.scene.Scene;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Stage extends javafx.stage.Stage {

    public Stage() {
    }

    public void showScene(Parent root, double width, double height, String title) {

        Scene scene = new Scene(root);
        this.setScene(scene);
        this.setTitle(title);
        this.setResizable(false);
        this.show();
    }


}
