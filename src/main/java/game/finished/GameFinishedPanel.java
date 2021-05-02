package main.java.game.finished;

import main.java.MainFrame;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * The type Game finished panel.
 */
public class GameFinishedPanel extends JPanel {
    private final Timer timer;

    private JLabel secondsUntilRestartLabel;

    /**
     * Instantiates a new Game finished panel.
     *
     * @param mainFrame                 the main frame
     * @param dimension                 the dimension
     * @param hasCheated                the has cheated
     * @param playerStepCount           the player step count
     * @param secondsUntilRestartStarts the seconds until restart starts
     */
    public GameFinishedPanel(MainFrame mainFrame, Dimension dimension, boolean hasCheated,
                             int playerStepCount, int secondsUntilRestartStarts) {
        super();

        setPreferredSize(dimension);
        setLayout(new GridBagLayout());

        displayMessage(hasCheated, playerStepCount, secondsUntilRestartStarts);

        timer = new Timer(1000, new GameFinishedPanelActionListener(mainFrame, this, secondsUntilRestartStarts));
        timer.start();
    }

    /**
     * Displays message.
     *
     * @param hasCheated whether has cheated
     * @param playerStepCount the player count
     * @param secondsUntilRestartStart the seconds until restart
     */
    private void displayMessage(boolean hasCheated, int playerStepCount, int secondsUntilRestartStart) {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;

        setMainLabel(hasCheated, playerStepCount, gridBagConstraints);
        setSecondsUntilRestartLabel(gridBagConstraints, secondsUntilRestartStart);
    }

    /**
     * Sets main label.
     *
     * @param hasCheated whether has cheated
     * @param playerStepCount the player count
     * @param gridBagConstraints the grid bag constraints
     */
    private void setMainLabel(boolean hasCheated, int playerStepCount, GridBagConstraints gridBagConstraints) {
        JLabel mainLabel;
        if (hasCheated) {
            mainLabel = new JLabel(GameFinishedPanelMessage.CHEATER.value);
        } else {
            mainLabel = new JLabel(String.format(GameFinishedPanelMessage.WINNER.value, playerStepCount));
        }

        mainLabel.setFont(new Font(mainLabel.getFont().getFontName(), Font.BOLD, 16));
        add(mainLabel, gridBagConstraints);
    }

    /**
     * Sets main label.
     *
     * @param gridBagConstraints the grid bag constraints
     * @param secondsUntilRestartStart the seconds until restart
     */
    private void setSecondsUntilRestartLabel(GridBagConstraints gridBagConstraints, int secondsUntilRestartStart) {
        secondsUntilRestartLabel = new JLabel(String.format(
                GameFinishedPanelMessage.RESTART_COUNTDOWN.value, secondsUntilRestartStart));
        add(secondsUntilRestartLabel, gridBagConstraints);
    }

    /**
     * Update winning massage part two.
     *
     * @param secondsUntilRestartFromNow the seconds until restart from now
     */
    void updateWinningMassagePartTwo(int secondsUntilRestartFromNow) {
        secondsUntilRestartLabel.setText(String.format(GameFinishedPanelMessage.RESTART_COUNTDOWN.value, secondsUntilRestartFromNow));
    }

    /**
     * Stop timer.
     */
    public void stopTimer() {
        timer.stop();
    }

    /**
     * Check if action event source is timer boolean.
     *
     * @param event the event
     * @return the boolean
     */
    boolean checkIfActionEventSourceIsTimer(ActionEvent event) {
        return event.getSource() == timer;
    }
}
