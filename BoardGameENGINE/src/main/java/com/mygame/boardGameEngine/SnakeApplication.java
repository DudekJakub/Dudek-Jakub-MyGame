package com.mygame.boardGameEngine;

import com.mygame.boardGameEngine.classes.scene.Stage;
import javafx.application.Application;
import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;

public class SnakeApplication extends Application {

    Stage mainStage;

    private Parent createContent() {



        return null;
    }





    @Override
    public void start(javafx.stage.Stage mainStage) throws Exception {
        this.mainStage = (Stage) mainStage;

        ((Stage) mainStage).showScene(createContent(), 600, 600, "Snake Application");
    }

    public static void main(String[] args) {

    }
}
