package main.java;

import main.java.game.finished.GameFinishedPanel;
import main.java.game.grid.GridOrder;
import main.java.game.grid.GridPanel;
import main.java.menu.MainMenuBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * The type Main frame.
 */
public class MainFrame extends JFrame {
    private GridPanel gridPanel;
    private GameFinishedPanel gameFinishedPanel;

    private GridOrder currentGridOrder;

    /**
     * Instantiates a new Main frame.
     */
    public MainFrame() {
        super("Rubik's Table");

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(loadJFrameIcon());

        setJMenuBar(new MainMenuBar(this));
        startNewGame(GridOrder.FOUR);

        enableAutoSolver();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Loads the icon for the Main frame.
     */
    private Image loadJFrameIcon() {
        return new ImageIcon(getClass().getResource("/main/resources/images/main-icon.png")).getImage();
    }

    /**
     * Starts a new game.
     */
    public void startNewGame() {
        startNewGame(currentGridOrder);
    }

    /**
     * Starts a new game.
     *
     * @param gridOrder the grid order
     */
    public void startNewGame(GridOrder gridOrder) {
        currentGridOrder = gridOrder;

        if (gameFinishedPanel != null) {
            getContentPane().remove(gameFinishedPanel);
            gameFinishedPanel.stopTimer();
            gameFinishedPanel = null;
        }

        if (gridPanel != null) {
            getContentPane().remove(gridPanel);
            gridPanel.stopTimer();
        }
        gridPanel = new GridPanel(this, new Dimension(480, 480), currentGridOrder);
        getContentPane().add(gridPanel, BorderLayout.CENTER);

        redraw();
    }

    /**
     * Ends the game.
     *
     * @param hasCheated      whether has cheated
     * @param playerStepCount the player step count
     */
    public void endGame(boolean hasCheated, int playerStepCount) {
        getContentPane().remove(gridPanel);
        gridPanel.stopTimer();

        gameFinishedPanel = new GameFinishedPanel(this, new Dimension(480, 480),
                hasCheated, playerStepCount, 5);
        getContentPane().add(gameFinishedPanel, BorderLayout.CENTER);

        redraw();
    }

    /**
     * Quits.
     */
    public void quit() {
        System.exit(0);
    }

    private void redraw() {
        pack();
        revalidate();
        repaint();
    }

    /**
     * Enables the auto solver.
     */
    private void enableAutoSolver() {
        KeyEventDispatcher keyEventDispatcher = e -> {
            if (e.getID() == KeyEvent.KEY_PRESSED && e.getModifiersEx() == 0) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_0 -> gridPanel.autoSolve(0);
                    case KeyEvent.VK_1 -> gridPanel.autoSolve(1);
                    case KeyEvent.VK_2 -> gridPanel.autoSolve(2);
                    case KeyEvent.VK_3 -> gridPanel.autoSolve(3);
                    case KeyEvent.VK_4 -> gridPanel.autoSolve(4);
                    case KeyEvent.VK_5 -> gridPanel.autoSolve(5);
                    case KeyEvent.VK_6 -> gridPanel.autoSolve(6);
                    case KeyEvent.VK_7 -> gridPanel.autoSolve(7);
                    case KeyEvent.VK_8 -> gridPanel.autoSolve(8);
                    case KeyEvent.VK_9 -> gridPanel.autoSolve(9);
                }
            }

            return false;
        };

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyEventDispatcher);
    }
}
