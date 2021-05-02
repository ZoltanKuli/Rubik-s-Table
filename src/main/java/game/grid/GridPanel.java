package main.java.game.grid;

import main.java.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * The type Grid panel.
 */
public class GridPanel extends JPanel {
    private final Timer timer;

    private final MainFrame mainFrame;

    private ArrayList<ArrayList<GridItem>> gridItems;
    private GridItemActionListener gridItemActionListener;

    /**
     * Instantiates a new Grid panel.
     *
     * @param mainFrame the main frame
     * @param dimension the dimension
     * @param gridOrder the grid order
     */
    public GridPanel(MainFrame mainFrame, Dimension dimension, GridOrder gridOrder) {
        super();

        this.mainFrame = mainFrame;

        setPreferredSize(dimension);
        setLayout(new GridLayout(gridOrder.value, gridOrder.value));

        setGridItems(gridOrder);

        timer = new Timer((int) 900 / gridOrder.value, gridItemActionListener);
    }

    /**
     * Sets the grid items.
     *
     * @param gridOrder the grid order
     */
    private void setGridItems(GridOrder gridOrder) {
        Dimension gridItemDimension = new Dimension(getWidth() / gridOrder.value, getHeight() / gridOrder.value);
        GridItemColor[] gridItemColors = GridItemColor.values();
        gridItemActionListener = new GridItemActionListener(mainFrame, this);

        gridItems = new ArrayList<>();
        for (int rowCoordinate = 0; rowCoordinate < gridOrder.value; rowCoordinate++) {
            setGridItemsRow(gridOrder, rowCoordinate, gridItemDimension, gridItemColors);
        }

        gridItemActionListener.setGridPanelGridItemsColor(copyGridItemsColor());
        redraw();
    }

    /**
     * Sets the grid items row.
     *
     * @param gridOrder the grid order
     * @param rowCoordinate the row coordinate
     * @param gridItemDimension the grid item dimension
     * @param gridItemColors the grid item colors
     */
    private void setGridItemsRow(GridOrder gridOrder, int rowCoordinate, Dimension gridItemDimension,
                                 GridItemColor[] gridItemColors) {
        gridItems.add(new ArrayList<>());
        for (int columnCoordinate = 0; columnCoordinate < gridOrder.value; columnCoordinate++) {
            GridItem gridItem = new GridItem(rowCoordinate, columnCoordinate);
            setGridItemDefaultProperties(gridItem, gridItemDimension, gridItemColors[rowCoordinate]);
            gridItem.addActionListener(gridItemActionListener);

            gridItems.get(rowCoordinate).add(gridItem);
            add(gridItem);
        }
    }

    /**
     * Sets the grid item default properties.
     *
     * @param gridItem the grid item
     * @param gridItemDimension the grid item dimension
     * @param gridItemColor the grid item color
     */
    private void setGridItemDefaultProperties(GridItem gridItem, Dimension gridItemDimension, GridItemColor gridItemColor) {
        gridItem.setPreferredSize(gridItemDimension);
        gridItem.setBackground(gridItemColor.value);
        gridItem.setDefaultCapable(false);
        gridItem.setFocusPainted(false);
    }

    /**
     * Update grid item colors.
     *
     * @param gridItemsColor the grid items color
     */
    void updateGridItemColors(ArrayList<ArrayList<GridItemColor>> gridItemsColor) {
        for (int i = 0; i < gridItems.size(); i++) {
            for (int j = 0; j < gridItems.size(); j++) {
                gridItems.get(i).get(j).setBackground(gridItemsColor.get(i).get(j).value);
            }
        }

        redraw();
    }

    /**
     * Redraw.
     */
    void redraw() {
        revalidate();
        repaint();
    }

    /**
     * Copies the grid items color.
     *
     * @return the grid items color
     */
    private ArrayList<ArrayList<GridItemColor>> copyGridItemsColor() {
        ArrayList<ArrayList<GridItemColor>> GridItemsColor = new ArrayList<>();

        for (int rowCoordinate = 0; rowCoordinate < gridItems.size(); rowCoordinate++) {
            GridItemsColor.add(new ArrayList<>());
            for (int columnCoordinate = 0; columnCoordinate < gridItems.size(); columnCoordinate++) {
                GridItemsColor.get(rowCoordinate).add(GridItemColor.getMatchingGridItemColor(
                        gridItems.get(rowCoordinate).get(columnCoordinate).getBackground()));
            }
        }

        return GridItemsColor;
    }

    /**
     * Auto solve.
     *
     * @param stepsToLeave the steps to leave
     */
    public void autoSolve(int stepsToLeave) {
        if (!gridItemActionListener.getHasCheated() && gridItems.size() > 2) {
            if (stepsToLeave == 0) {
                removeGridItemActionListenerFromGridItems();
            }

            gridItemActionListener.setStepsToLeaveWhenAutoSolve(stepsToLeave);

            timer.start();
        }
    }

    private void removeGridItemActionListenerFromGridItems() {
        for (ArrayList<GridItem> gridItemsRow : gridItems) {
            for (GridItem gridItem : gridItemsRow) {
                gridItem.removeActionListener(gridItemActionListener);
            }
        }
    }

    /**
     * Sets timer delay.
     *
     * @param delay the delay
     */
    public void setTimerDelay(int delay) {
        timer.setDelay(delay);
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
