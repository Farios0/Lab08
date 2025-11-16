package it.unibo.mvc.view;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.api.DrawResult;

public class DrawNumberStandardOutputView implements DrawNumberView{

    private static final String NEW_GAME = ": a new game starts! (type quit to exit)";
    private DrawNumberController controller;

    @Override
    public void setController(DrawNumberController observer) {
        this.controller = observer;
    }

    @Override
    public void start() {
        System.out.println("Try to guess my number!");
        String attempt = System.console().readLine();
        while(!attempt.equals("quit")) {
            controller.newAttempt(Integer.parseInt(attempt));
            attempt = System.console().readLine();
        }
    }

    @Override
    public void result(DrawResult res) {
        switch (res) {
            case YOURS_HIGH, YOURS_LOW -> {
                System.out.println(res.getDescription());
                return;
            }
            case YOU_WON -> System.out.println(res.getDescription() + NEW_GAME);
            case YOU_LOST -> System.out.println(res.getDescription() + NEW_GAME);
        }
        controller.resetGame();
    }
        
}

    
