package main.java.menu;

import main.java.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Main menu item action listener.
 */
class MainMenuItemActionListener implements ActionListener {
    private final MainFrame mainFrame;

    /**
     * Instantiates a new Main menu item action listener.
     *
     * @param mainFrame the main frame
     */
    MainMenuItemActionListener(MainFrame mainFrame) {
        super();

        this.mainFrame = mainFrame;
    }

    /**
     * Based on the action event source, executes the appropriate main frame method.
     *
     * @param actionEvent the action event
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JMenuItem jMenuItem = (JMenuItem) actionEvent.getSource();
        if (jMenuItem instanceof NewGameMenuItem) {
            NewGameMenuItem newGameMenuItem = (NewGameMenuItem) jMenuItem;
            mainFrame.startNewGame(newGameMenuItem.getGridOrder());
        } else if (jMenuItem instanceof QuitGameMenuItem) {
            mainFrame.quit();
        }
    }
}
