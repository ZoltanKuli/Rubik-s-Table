package main.java.game.finished;

import main.java.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Game finished panel action listener.
 */
class GameFinishedPanelActionListener implements ActionListener {
    private final MainFrame mainFrame;
    private final GameFinishedPanel gameFinishedPanel;
    private int secondsUntilRestartStarts;


    /**
     * Instantiates a new Game finished panel action listener.
     *
     * @param mainFrame                 the main frame
     * @param gameFinishedPanel         the game finished panel
     * @param secondsUntilRestartStarts the seconds until restart starts
     */
    GameFinishedPanelActionListener(MainFrame mainFrame, GameFinishedPanel gameFinishedPanel, int secondsUntilRestartStarts) {
        this.mainFrame = mainFrame;
        this.gameFinishedPanel = gameFinishedPanel;
        this.secondsUntilRestartStarts = secondsUntilRestartStarts;
    }

    /**
     * Updates the winning message part two when action event source is timer.
     *
     * @param actionEvent               the action event
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (gameFinishedPanel.checkIfActionEventSourceIsTimer(actionEvent)) {
            if (secondsUntilRestartStarts > 0) {
                gameFinishedPanel.updateWinningMassagePartTwo(--secondsUntilRestartStarts);
            } else {
                mainFrame.startNewGame();
            }
        }
    }
}
