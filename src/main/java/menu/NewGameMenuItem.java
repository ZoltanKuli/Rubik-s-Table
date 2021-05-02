package main.java.menu;

import main.java.game.grid.GridOrder;

import javax.swing.*;

/**
 * The type New game menu item.
 */
class NewGameMenuItem extends JMenuItem {
    private final GridOrder gridOrder;

    /**
     * Instantiates a new New game menu item.
     *
     * @param gridOrder the grid order
     */
    NewGameMenuItem(GridOrder gridOrder) {
        super(String.format(MainMenuElementText.INDIVIDUAL_NEW_GAME.value,
                gridOrder.value, gridOrder.value));

        this.gridOrder = gridOrder;
    }

    /**
     * Gets grid order.
     *
     * @return the grid order
     */
    GridOrder getGridOrder() {
        return gridOrder;
    }
}
