package it.unibo.mvc.view;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.api.DrawResult;

/**
 * this is a view that uses the terminal to comunicate with the user.
 */
public final class DrawNumberStandardOutputView implements DrawNumberView {

    private static final String NEW_GAME = ": a new game starts! (type quit to exit)";
    private DrawNumberController controller;

    @Override
    public void setController(final DrawNumberController observer) {
        this.controller = observer;
    }

    @Override
    public void start() {
        System.out.println("Try to guess my number!"); // NOPMD
        // this view is supposed to print on terminal
        String attempt = System.console().readLine();
        while (!"quit".equals(attempt)) {
            controller.newAttempt(Integer.parseInt(attempt));
            attempt = System.console().readLine();
        }
    }

    @Override
    public void result(final DrawResult res) {
        switch (res) {
            case YOURS_HIGH, YOURS_LOW -> {
                System.out.println(res.getDescription()); // NOPMD
                // this view is supposed to print on terminal
                return;
            }
            case YOU_WON -> System.out.println(res.getDescription() + NEW_GAME); // NOPMD
            // this view is supposed to print on terminal
            case YOU_LOST -> System.out.println(res.getDescription() + NEW_GAME); // NOPMD
            // this view is supposed to print on terminal
        }
        controller.resetGame();
    }

}

