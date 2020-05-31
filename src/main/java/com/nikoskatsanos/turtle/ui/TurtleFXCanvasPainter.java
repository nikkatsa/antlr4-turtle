package com.nikoskatsanos.turtle.ui;

import com.nikoskatsanos.turtle.TurtlePainter;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class TurtleFXCanvasPainter implements TurtlePainter {

    private final Region sceneGraph;
    private final Group canvas;
    private final double width;
    private final double height;

    private double direction = 0;// 0 degrees == EAST
    private final Circle turtle;

    private boolean isPenDown = true;

    private final SequentialTransition animation = new SequentialTransition();
    private long animationDurationMs = 100;

    public TurtleFXCanvasPainter(Group canvas) {
        this.canvas = canvas;
        this.sceneGraph = (Region) this.canvas.getParent();
        this.width = this.sceneGraph.getMaxWidth();
        this.height = this.sceneGraph.getMaxHeight();

        this.turtle = new Circle(this.width / 2, this.height / 2, 5, Color.RED);

        this.paintTurtle(this.turtle.getCenterX(), this.turtle.getCenterY());

        this.animation.setCycleCount(1);
    }

    @Override
    public void forward(int points) {
        JavaFXThreadHelper.runOrDefer(() -> {
            final double radian = this.toRadian(this.direction);
            final double x = this.turtle.getCenterX() + points * Math.cos(radian);
            final double y = this.turtle.getCenterY() - points * Math.sin(radian);

            this.validateBounds(x, y);

            this.moveTurtle(x, y);
        });
    }

    private void moveTurtle(final double x, final double y) {
        JavaFXThreadHelper.runOrDefer(() -> {

            final Path path = new Path();
            path.getElements().add(new MoveTo(this.turtle.getCenterX(), this.turtle.getCenterY()));
            path.getElements().add(new LineTo(x, y));

            final PathTransition pathTransition = new PathTransition();
            pathTransition.setDuration(Duration.millis(this.animationDurationMs));
            pathTransition.setPath(path);
            pathTransition.setNode(this.turtle);

            if (this.isPenDown) {
                final Line line = new Line(this.turtle.getCenterX(), this.turtle.getCenterY(), x, y);
                pathTransition.setOnFinished(onFinished -> this.canvas.getChildren().add(line));
            }

            animation.getChildren().add(pathTransition);

            this.paintTurtle(x, y);
        });
    }

    @Override
    public void back(final int points) {
        Platform.runLater(() -> {
            final double radian = this.toRadian(this.direction);
            final double x = this.turtle.getCenterX() - points * Math.cos(radian);
            final double y = this.turtle.getCenterY() + points * Math.sin(radian);

            this.validateBounds(x, y);

            this.moveTurtle(x, y);
        });
    }

    @Override
    public void right(final int degrees) {
        Platform.runLater(() -> this.direction = (this.direction - degrees) % 360);
    }

    @Override
    public void left(final int degrees) {
        Platform.runLater(() -> this.direction = (this.direction + degrees) % 360);
    }

    @Override
    public void set(final int x, final int y) {
        JavaFXThreadHelper.runOrDefer(() -> {
            final boolean wasPenDown = this.isPenDown;
            if (this.isPenDown) {
                this.penUp();
            }
            this.moveTurtle(x, y);
            if (wasPenDown) {
                this.penDown();
            }
        });
    }

    @Override
    public void penUp() {
        JavaFXThreadHelper.runOrDefer(() -> this.isPenDown = false);
    }

    @Override
    public void penDown() {
        JavaFXThreadHelper.runOrDefer(() -> this.isPenDown = true);
    }

    @Override
    public void cls() {
        JavaFXThreadHelper.runOrDefer(() -> {
            this.animation.getChildren().clear();
            this.canvas.getChildren().clear();
            this.paintTurtle(this.sceneGraph.getWidth() / 2, this.sceneGraph.getHeight() / 2);
        });
    }

    @Override
    public void resetAngle() {
        JavaFXThreadHelper.runOrDefer(()-> this.direction = 0);
    }

    @Override
    public void finish() {
        JavaFXThreadHelper.runOrDefer(this.animation::play);
    }

    private void validateBounds(final double newX, final double newY) {
        if ((newX < 0 || newX > this.width) || (newY < 0 || newY > this.height)) {
            throw new IllegalArgumentException(
                String.format("Action results in turtle out of canvas. CanvasDimensions=(%f, %f), TurtleCurrentPosition=(%f, %f), NewPosition=(%f, %f)",
                    this.width, this.height, this.turtle.getCenterX(), this.turtle.getCenterY(), newX, newY));
        }
    }

    private void paintTurtle(final double x, final double y) {
        JavaFXThreadHelper.runOrDefer(() -> {
            this.turtle.setCenterX(x);
            this.turtle.setCenterY(y);

            this.canvas.getChildren().remove(this.turtle);
            this.canvas.getChildren().add(this.turtle);
        });
    }

    private double toRadian(final double direction) {
        return direction * Math.PI / 180;
    }

    public void setAnimationDurationMs(final long animationDurationMs) {
        this.animationDurationMs = animationDurationMs;
    }
}
