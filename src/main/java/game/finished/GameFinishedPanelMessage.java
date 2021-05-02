package main.java.game.finished;

/**
 * The enum Game finished panel message.
 */
enum GameFinishedPanelMessage {
    /**
     * The Winner.
     */
    WINNER("<html>WINNER WINNER CHICKEN DINNER IN %d STEPS!!<br/></html>"),
    /**
     * The Cheater.
     */
    CHEATER("<html>WINNER WINNER... SNEAKY CHEATER...<br/></html>"),
    /**
     * The Restart countdown.
     */
    RESTART_COUNTDOWN("New Game Starts in %d Seconds...");

    /**
     * The Value.
     */
    final String value;

    GameFinishedPanelMessage(String value) {
        this.value = value;
    }
}
