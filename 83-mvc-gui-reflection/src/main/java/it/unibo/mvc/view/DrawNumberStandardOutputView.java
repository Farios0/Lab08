package it.unibo.mvc.view;

import java.util.Objects;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.api.DrawResult;

/**
 * this is a view that uses the terminal to comunicate with the user.
 */
public final class DrawNumberStandardOutputView implements DrawNumberView {

    private static final String NEW_GAME = ": a new game starts! (type quit to exit)";
    private DrawNumberController controller;

    /**
     * builds a new istance of this class by initialazing the controller.
     * 
     * @param controller is the controller you want to use for this view
     */
    public DrawNumberStandardOutputView(final DrawNumberController controller) {
        setController(controller);
    }

    /**
     * Constructor to use in reflection.
     */

    public DrawNumberStandardOutputView() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    @Override
    public void setController(final DrawNumberController observer) {
        this.controller = Objects.requireNonNull(observer, "controller cannot be null");
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
            case YOU_WON, YOU_LOST -> System.out.println(res.getDescription() + NEW_GAME); // NOPMD
            // this view is supposed to print on terminal
        }
        controller.resetGame();
    }

}

