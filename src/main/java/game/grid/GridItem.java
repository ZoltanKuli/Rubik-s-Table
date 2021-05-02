package main.java.game.grid;

import javax.swing.*;

/**
 * The type Grid item.
 */
class GridItem extends JButton {
    private final int rowCoordinate;
    private final int columnCoordinate;

    /**
     * Instantiates a new Grid item.
     *
     * @param rowCoordinate    the row coordinate
     * @param columnCoordinate the column coordinate
     */
    GridItem(int rowCoordinate, int columnCoordinate) {
        super();

        this.rowCoordinate = rowCoordinate;
        this.columnCoordinate = columnCoordinate;
    }

    /**
     * Gets row coordinate.
     *
     * @return the row coordinate
     */
    int getRowCoordinate() {
        return rowCoordinate;
    }

    /**
     * Gets column coordinate.
     *
     * @return the column coordinate
     */
    int getColumnCoordinate() {
        return columnCoordinate;
    }
}
