package com.nikoskatsanos.turtle.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TurtleFXUI extends Application {

    @Override
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setTitle("Logo Turtle");
        primaryStage.centerOnScreen();
        primaryStage.setOnCloseRequest(windowEvent -> System.exit(0));

        final FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/LogoTurtle.fxml"));
        final Parent parent = fxmlLoader.load();
        final Scene primaryScene = new Scene(parent, 650, 500);

        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }
}
