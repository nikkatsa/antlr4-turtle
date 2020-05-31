package com.nikoskatsanos.turtle.ui;

import javafx.application.Platform;

public class JavaFXThreadHelper {

    private JavaFXThreadHelper() {
    }

    public static void runOrDefer(final Runnable runnable) {
        if (Platform.isFxApplicationThread()) {
            runnable.run();
        } else {
            Platform.runLater(runnable);
        }
    }
}
