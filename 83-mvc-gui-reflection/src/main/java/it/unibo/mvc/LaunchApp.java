package it.unibo.mvc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.controller.DrawNumberControllerImpl;
import it.unibo.mvc.model.DrawNumberImpl;

/**
 * Application entry-point.
 */
public final class LaunchApp {

    private static final String SWING_VIEW = "it.unibo.mvc.view.DrawNumberSwingView";
    private static final String STDOUT_VIEW = "it.unibo.mvc.view.DrawNumberStandardOutputView";

    private LaunchApp() { }

    /**
     * Runs the application.
     *
     * @param args ignored
     * @throws ClassNotFoundException if the fetches class does not exist
     * @throws NoSuchMethodException if the 0-ary constructor do not exist
     * @throws InvocationTargetException if the constructor throws exceptions
     * @throws InstantiationException if the constructor throws exceptions
     * @throws IllegalAccessException in case of reflection issues
     * @throws IllegalArgumentException in case of reflection issues
     */
    public static void main(final String... args) throws Exception{
        final var model = new DrawNumberImpl();
        final DrawNumberController app = new DrawNumberControllerImpl(model);
        Class<?> swingView = Class.forName(SWING_VIEW);
        Class<?> stdOutView = Class.forName(STDOUT_VIEW);
        List<Constructor<?>> costruttori = new ArrayList<>();
        costruttori.add(swingView.getConstructor());
        costruttori.add(stdOutView.getConstructor());
        for (int i = 0; i < 30; i++) {
            for (var c : costruttori) {
                final DrawNumberView view = (DrawNumberView) c.newInstance();
                app.addView(view);
                }
        }


    }
}
