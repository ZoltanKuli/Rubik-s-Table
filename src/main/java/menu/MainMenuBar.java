package main.java.menu;

import main.java.MainFrame;
import main.java.game.grid.GridOrder;

import javax.swing.*;

/**
 * The type Main menu bar.
 */
public class MainMenuBar extends JMenuBar {
    private final MainFrame mainFrame;

    /**
     * Instantiates a new Main menu bar.
     *
     * @param mainFrame the main frame
     */
    public MainMenuBar(MainFrame mainFrame) {
        super();

        this.mainFrame = mainFrame;

        addMenus();
    }

    /**
     * Adds the menus.
     */
    private void addMenus() {
        JMenu mainMenu = new JMenu(MainMenuElementText.MENU.value);
        add(mainMenu);

        addNewGameMenu(mainMenu);
        addQuitGameMenu(mainMenu);
    }

    /**
     * Adds a new game menu.
     *
     * @param mainMenu the main menu.
     */
    private void addNewGameMenu(JMenu mainMenu) {
        JMenu newGameMenu = new JMenu(MainMenuElementText.NEW_GAME.value);
        mainMenu.add(newGameMenu);

        addNewGameMenuItems(newGameMenu);
    }

    /**
     * Adds new game menu items.
     *
     * @param newGameMenu the new game menu.
     */
    private void addNewGameMenuItems(JMenu newGameMenu) {
        GridOrder[] GridOrders = GridOrder.values();
        for (GridOrder gridOrder : GridOrders) {
            NewGameMenuItem newGameMenuMenuItem = new NewGameMenuItem(gridOrder);
            newGameMenuMenuItem.addActionListener(new MainMenuItemActionListener(mainFrame));

            newGameMenu.add(newGameMenuMenuItem);
        }
    }

    /**
     * Adds a quit menu.
     *
     * @param mainMenu the main menu.
     */
    private void addQuitGameMenu(JMenu mainMenu) {
        QuitGameMenuItem quitMenuItem = new QuitGameMenuItem();
        quitMenuItem.addActionListener(new MainMenuItemActionListener(mainFrame));

        mainMenu.add(quitMenuItem);
    }
}
