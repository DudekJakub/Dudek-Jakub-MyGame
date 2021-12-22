package com.mygame.boardgame;

import com.mygame.boardgame.classes.Stage.RootContent;
import com.mygame.boardgame.classes.Stage.Stage;
import javafx.application.Application;
import javafx.scene.Parent;

public class SnakeApplication extends Application {

    Stage mainStage;
    RootContent rootContent;

    private Parent createContent() {

        rootContent.setRoot();


        return rootContent.getRoot();
    }





    @Override
    public void start(javafx.stage.Stage mainStage) throws Exception {
        this.mainStage = (Stage) mainStage;

        ((Stage) mainStage).showScene(createContent(), 600, 600, "Snake Application");
    }

    public static void main(String[] args) {

    }
}
