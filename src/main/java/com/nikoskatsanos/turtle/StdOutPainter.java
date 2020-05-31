package com.nikoskatsanos.turtle;

public class StdOutPainter implements TurtlePainter {

    @Override
    public void forward(final int points) {
        System.out.println("Forward " + points);
    }

    @Override
    public void back(final int points) {
        System.out.println("Back " + points);
    }

    @Override
    public void right(final int degrees) {
        System.out.println("Right " + degrees);
    }

    @Override
    public void left(final int degrees) {
        System.out.println("Left " + degrees);
    }

    @Override
    public void set(final int x, final int y) {
        System.out.println("Set " + x + ", " + y);
    }

    @Override
    public void penUp() {
        System.out.println("PenUp");
    }

    @Override
    public void penDown() {
        System.out.println("PenDown");
    }

    @Override
    public void cls() {
        System.out.println("Cls");
    }

    @Override
    public void resetAngle() {
        System.out.println("ResetAngle");
    }

    @Override
    public void finish() {
    }
}
