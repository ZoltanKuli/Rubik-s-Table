package main.java.game.grid;

import main.java.MainFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The type Grid item action listener.
 */
class GridItemActionListener implements ActionListener {
    private final MainFrame mainFrame;
    private final GridPanel gridPanel;
    private final int outOfBoundsPressedGridItemCoordinate;
    private ArrayList<ArrayList<GridItemColor>> gridPanelGridItemsColor;
    private ArrayList<ArrayList<Integer>> stepsRecord;
    private int stepsToLeaveWhenAutoSolve;
    private boolean hasCheated;
    private int playerStepsCount;
    private int previouslyPressedGridItemRowCoordinate;
    private int previouslyPressedGridItemColumnCoordinate;
    private int currentlyPressedGridItemRowCoordinate;
    private int currentlyPressedGridItemColumnCoordinate;

    /**
     * Instantiates a new Grid item action listener.
     *
     * @param mainFrame the main frame
     * @param gridPanel the grid panel
     */
    GridItemActionListener(MainFrame mainFrame, GridPanel gridPanel) {
        super();

        this.mainFrame = mainFrame;
        this.gridPanel = gridPanel;

        outOfBoundsPressedGridItemCoordinate = -1;
        setOutOfBoundsPressedGridItemCoordinates();

        hasCheated = false;
        playerStepsCount = 0;
    }

    /**
     * Sets grid panel grid items color.
     *
     * @param gridPanelGridItemsColor the grid panel grid items color
     */
    void setGridPanelGridItemsColor(ArrayList<ArrayList<GridItemColor>> gridPanelGridItemsColor) {
        this.gridPanelGridItemsColor = gridPanelGridItemsColor;

        shuffleGridPanelGridItemsColor();

        gridPanel.updateGridItemColors(gridPanelGridItemsColor);
    }

    /**
     * Shuffles grid panel grid items color.
     */
    private void shuffleGridPanelGridItemsColor() {
        stepsRecord = new ArrayList<>();

        if (gridPanelGridItemsColor.size() == GridOrder.TWO.value) {
            updateStepsRecord(Rotation.UPWARD, 0, 1);
            upwardRotateColumnBy(stepsRecord.get(0).get(1), stepsRecord.get(0).get(2));
        } else {
            int toBeRotatedByLowerBoundary = 1;
            int toBeRotatedByUpperBoundary = gridPanelGridItemsColor.size() * 5;

            for (int i = 0; i < (gridPanelGridItemsColor.size() * gridPanelGridItemsColor.size()); i++) {
                int randomRotationDirection = ThreadLocalRandom.current().nextInt(0, 4);

                if (randomRotationDirection == Rotation.BACKWARD.value) {
                    updateStepsRecord(Rotation.BACKWARD, ThreadLocalRandom.current().nextInt(0, gridPanelGridItemsColor.size()),
                            ThreadLocalRandom.current().nextInt(toBeRotatedByLowerBoundary, toBeRotatedByUpperBoundary));

                    backwardRotateRowBy(stepsRecord.get(i).get(1), stepsRecord.get(i).get(2));
                } else if (randomRotationDirection == Rotation.UPWARD.value) {
                    updateStepsRecord(Rotation.UPWARD, ThreadLocalRandom.current().nextInt(0, gridPanelGridItemsColor.size()),
                            ThreadLocalRandom.current().nextInt(toBeRotatedByLowerBoundary, toBeRotatedByUpperBoundary));

                    upwardRotateColumnBy(stepsRecord.get(i).get(1), stepsRecord.get(i).get(2));
                } else if (randomRotationDirection == Rotation.FORWARD.value) {
                    updateStepsRecord(Rotation.FORWARD, ThreadLocalRandom.current().nextInt(0, gridPanelGridItemsColor.size()),
                            ThreadLocalRandom.current().nextInt(toBeRotatedByLowerBoundary, toBeRotatedByUpperBoundary));

                    forwardRotateRowBy(stepsRecord.get(i).get(1), stepsRecord.get(i).get(2));
                } else if (randomRotationDirection == Rotation.DOWNWARD.value) {
                    updateStepsRecord(Rotation.DOWNWARD, ThreadLocalRandom.current().nextInt(0, gridPanelGridItemsColor.size()),
                            ThreadLocalRandom.current().nextInt(toBeRotatedByLowerBoundary, toBeRotatedByUpperBoundary));

                    downwardRotateColumnBy(stepsRecord.get(i).get(1), stepsRecord.get(i).get(2));
                }
            }
        }
    }

    /**
     * Updates steps record.
     */
    private void updateStepsRecord(Rotation rotation, int rowOrColumnCoordinate, int toBeRotatedBy) {
        stepsRecord.add(new ArrayList<>());
        stepsRecord.get(stepsRecord.size() - 1).add(rotation.value);
        stepsRecord.get(stepsRecord.size() - 1).add(rowOrColumnCoordinate);
        stepsRecord.get(stepsRecord.size() - 1).add(toBeRotatedBy);
    }

    /**
     * Executes logic based on user action.
     *
     * @param actionEvent the action event
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (gridPanel.checkIfActionEventSourceIsTimer(actionEvent)) {
            if (stepsRecord.size() == 2) {
                gridPanel.setTimerDelay(1600);
            }

            if (stepsRecord.size() > 0 && stepsRecord.size() > stepsToLeaveWhenAutoSolve) {
                traceBackAStep();
            } else if (stepsRecord.size() == stepsToLeaveWhenAutoSolve && stepsRecord.size() != 0) {
                gridPanel.stopTimer();
                hasCheated = true;
            } else {
                hasCheated = true;
                mainFrame.endGame(true, 0);
            }
        } else {
            GridItem gridItem = (GridItem) actionEvent.getSource();

            updatePressedGridItemCoordinates(gridItem);
            rotateGridPanelGridItemsColor();

            if (checkIfSolved()) {
                mainFrame.endGame(hasCheated, playerStepsCount);
            }
        }
    }

    /**
     * Updates pressed grid item coordinates.
     *
     * @param gridItem the grid item
     */
    private void updatePressedGridItemCoordinates(GridItem gridItem) {
        if (checkIfPreviouslyPressedGritItemCoordinatesAreOutOfBounds()) {
            updatePreviouslyPressedGridItemCoordinates(gridItem);
        } else if (checkIfCurrentlyPressedGritItemCoordinatesAreOutOfBounds()) {
            updateCurrentlyPressedGridItemCoordinates(gridItem);
        }
    }

    /**
     * Checks if previously pressed grit item coordinates are out of bounds.
     */
    private boolean checkIfPreviouslyPressedGritItemCoordinatesAreOutOfBounds() {
        return previouslyPressedGridItemRowCoordinate == outOfBoundsPressedGridItemCoordinate &&
                previouslyPressedGridItemColumnCoordinate == outOfBoundsPressedGridItemCoordinate;
    }

    /**
     * Updates the previously pressed grid item coordinates.
     *
     * @param gridItem the grid item
     */
    private void updatePreviouslyPressedGridItemCoordinates(GridItem gridItem) {
        previouslyPressedGridItemRowCoordinate = gridItem.getRowCoordinate();
        previouslyPressedGridItemColumnCoordinate = gridItem.getColumnCoordinate();

        gridItem.setFocusPainted(true);
    }

    /**
     * Checks if currently pressed grit item coordinates are out of bounds.
     */
    private boolean checkIfCurrentlyPressedGritItemCoordinatesAreOutOfBounds() {
        return currentlyPressedGridItemRowCoordinate == outOfBoundsPressedGridItemCoordinate &&
                currentlyPressedGridItemColumnCoordinate == outOfBoundsPressedGridItemCoordinate;
    }

    /**
     * Updates the currently pressed grid item coordinates.
     *
     * @param gridItem the grid item
     */
    private void updateCurrentlyPressedGridItemCoordinates(GridItem gridItem) {
        currentlyPressedGridItemRowCoordinate = gridItem.getRowCoordinate();
        currentlyPressedGridItemColumnCoordinate = gridItem.getColumnCoordinate();

        setOutOfBoundsPressedGridItemCoordinatesIfTheyAreDiagonal();

        gridItem.setFocusPainted(false);
    }

    /**
     * Sets out of bounds pressed grid item coordinates.
     */
    private void setOutOfBoundsPressedGridItemCoordinates() {
        previouslyPressedGridItemRowCoordinate = outOfBoundsPressedGridItemCoordinate;
        previouslyPressedGridItemColumnCoordinate = outOfBoundsPressedGridItemCoordinate;
        currentlyPressedGridItemRowCoordinate = outOfBoundsPressedGridItemCoordinate;
        currentlyPressedGridItemColumnCoordinate = outOfBoundsPressedGridItemCoordinate;
    }

    /**
     * Sets out of bounds pressed grid item coordinates if they are diagonal.
     */
    private void setOutOfBoundsPressedGridItemCoordinatesIfTheyAreDiagonal() {
        if (previouslyPressedGridItemRowCoordinate != currentlyPressedGridItemRowCoordinate &&
                previouslyPressedGridItemColumnCoordinate != currentlyPressedGridItemColumnCoordinate) {
            setOutOfBoundsPressedGridItemCoordinates();
        }
    }

    /**
     * Rotates grid panel grid items color.
     */
    private void rotateGridPanelGridItemsColor() {
        if (!checkIfPreviouslyPressedGritItemCoordinatesAreOutOfBounds() &&
                !checkIfCurrentlyPressedGritItemCoordinatesAreOutOfBounds()) {
            if (checkIfPressedGridItemsAreInSameRow()) {
                rotateGridPanelGridItemsColorRow();
            } else if (checkIfPressedGridItemsAreInSameColumn()) {
                rotateGridPanelGridItemColorsColumn();
            }

            gridPanel.updateGridItemColors(gridPanelGridItemsColor);
            setOutOfBoundsPressedGridItemCoordinates();

            playerStepsCount++;
        }
    }

    /**
     * Checks if pressed grid items are in same row.
     */
    private boolean checkIfPressedGridItemsAreInSameRow() {
        return previouslyPressedGridItemRowCoordinate == currentlyPressedGridItemRowCoordinate;
    }

    /**
     * Checks if pressed grid items are in same column.
     */
    private boolean checkIfPressedGridItemsAreInSameColumn() {
        return previouslyPressedGridItemColumnCoordinate == currentlyPressedGridItemColumnCoordinate;
    }

    /**
     * Rotates grid panel grid items color row.
     */
    private void rotateGridPanelGridItemsColorRow() {
        int toBeRotatedBy = previouslyPressedGridItemColumnCoordinate - currentlyPressedGridItemColumnCoordinate;

        if (toBeRotatedBy < 0) {
            forwardRotateRowBy(currentlyPressedGridItemRowCoordinate, Math.abs(toBeRotatedBy));

            updateStepsRecord(Rotation.FORWARD, currentlyPressedGridItemRowCoordinate, Math.abs(toBeRotatedBy));
        } else if (0 < toBeRotatedBy) {
            backwardRotateRowBy(currentlyPressedGridItemRowCoordinate, toBeRotatedBy);

            updateStepsRecord(Rotation.BACKWARD, currentlyPressedGridItemRowCoordinate, toBeRotatedBy);
        }
    }

    /**
     * Backward rotates row by amount.
     *
     * @param rowIndex      the row index
     * @param toBeRotatedBy the amount to be rotated by
     */
    private void backwardRotateRowBy(int rowIndex, int toBeRotatedBy) {
        for (int i = 0; i < toBeRotatedBy; i++) {
            backwardRotateRowByOne(rowIndex);
        }
    }

    /**
     * Backward rotates row by one.
     *
     * @param rowIndex the row index
     */
    private void backwardRotateRowByOne(int rowIndex) {
        GridItemColor tmpGridItemColor = gridPanelGridItemsColor.get(rowIndex).get(0);

        int i = 0;
        for (; i < gridPanelGridItemsColor.size() - 1; i++) {
            gridPanelGridItemsColor.get(rowIndex).set(i, gridPanelGridItemsColor.get(rowIndex).get(i + 1));
        }

        gridPanelGridItemsColor.get(rowIndex).set(i, tmpGridItemColor);
    }

    /**
     * Forward rotates row by amount.
     *
     * @param rowIndex      the row index
     * @param toBeRotatedBy the amount to be rotated by
     */
    private void forwardRotateRowBy(int rowIndex, int toBeRotatedBy) {
        for (int i = toBeRotatedBy; i > 0; i--) {
            forwardRotateRowByOne(rowIndex);
        }
    }

    /**
     * Forward rotates row by one.
     *
     * @param rowIndex the row index
     */
    private void forwardRotateRowByOne(int rowIndex) {
        GridItemColor tmpGridItemColor = gridPanelGridItemsColor.get(rowIndex).get(gridPanelGridItemsColor.size() - 1);

        int i = gridPanelGridItemsColor.size() - 1;
        for (; i > 0; i--) {
            gridPanelGridItemsColor.get(rowIndex).set(i, gridPanelGridItemsColor.get(rowIndex).get(i - 1));
        }

        gridPanelGridItemsColor.get(rowIndex).set(i, tmpGridItemColor);
    }

    /**
     * Rotates grid panel grid item colors column.
     */
    private void rotateGridPanelGridItemColorsColumn() {
        int toBeRotatedBy = previouslyPressedGridItemRowCoordinate - currentlyPressedGridItemRowCoordinate;

        if (toBeRotatedBy < 0) {
            downwardRotateColumnBy(currentlyPressedGridItemColumnCoordinate, Math.abs(toBeRotatedBy));

            updateStepsRecord(Rotation.DOWNWARD, currentlyPressedGridItemColumnCoordinate, Math.abs(toBeRotatedBy));
        } else if (0 < toBeRotatedBy) {
            upwardRotateColumnBy(currentlyPressedGridItemColumnCoordinate, toBeRotatedBy);

            updateStepsRecord(Rotation.UPWARD, currentlyPressedGridItemColumnCoordinate, toBeRotatedBy);
        }
    }

    /**
     * Upward rotates row by amount.
     *
     * @param columnIndex   the row index
     * @param toBeRotatedBy the amount to be rotated by
     */
    private void upwardRotateColumnBy(int columnIndex, int toBeRotatedBy) {
        for (int i = 0; i < toBeRotatedBy; i++) {
            upwardRotateColumnByOne(columnIndex);
        }
    }

    /**
     * Upward rotates row by one.
     *
     * @param columnIndex the row index
     */
    private void upwardRotateColumnByOne(int columnIndex) {
        GridItemColor tmpGridItemColor = gridPanelGridItemsColor.get(0).get(columnIndex);

        int i = 0;
        for (; i < gridPanelGridItemsColor.size() - 1; i++) {
            gridPanelGridItemsColor.get(i).set(columnIndex, gridPanelGridItemsColor.get(i + 1).get(columnIndex));
        }

        gridPanelGridItemsColor.get(i).set(columnIndex, tmpGridItemColor);
    }

    /**
     * Downward rotates row by amount.
     *
     * @param columnIndex   the row index
     * @param toBeRotatedBy the amount to be rotated by
     */
    private void downwardRotateColumnBy(int columnIndex, int toBeRotatedBy) {
        for (int i = toBeRotatedBy; i > 0; i--) {
            downwardRotateColumnByOne(columnIndex);
        }
    }

    /**
     * Downward rotates row by one.
     *
     * @param columnIndex the row index
     */
    private void downwardRotateColumnByOne(int columnIndex) {
        GridItemColor tmpGridItemColor = gridPanelGridItemsColor.get(gridPanelGridItemsColor.size() - 1).get(columnIndex);

        int i = gridPanelGridItemsColor.size() - 1;
        for (; i > 0; i--) {
            gridPanelGridItemsColor.get(i).set(columnIndex, gridPanelGridItemsColor.get(i - 1).get(columnIndex));
        }

        gridPanelGridItemsColor.get(i).set(columnIndex, tmpGridItemColor);
    }


    /**
     * Checks if solved.
     */
    private boolean checkIfSolved() {
        return checkIfRowsSolved() || checkIfColumnsSolved();
    }

    /**
     * Checks if rows solved.
     */
    private boolean checkIfRowsSolved() {
        Color[] rowColors = new Color[gridPanelGridItemsColor.size()];
        for (int rowIndex = 0; rowIndex < rowColors.length; rowIndex++) {
            rowColors[rowIndex] = gridPanelGridItemsColor.get(rowIndex).get(0).value;
        }

        boolean areRowsSolved = true;
        for (int rowIndex = 1; rowIndex < gridPanelGridItemsColor.size() && areRowsSolved; rowIndex++) {
            for (int columnIndex = 0; columnIndex < gridPanelGridItemsColor.size(); columnIndex++) {
                if (rowColors[rowIndex] != gridPanelGridItemsColor.get(rowIndex).get(columnIndex).value) {
                    areRowsSolved = false;
                    break;
                }
            }
        }

        return areRowsSolved;
    }

    /**
     * Checks if columns solved.
     */
    private boolean checkIfColumnsSolved() {
        Color[] columnColors = new Color[gridPanelGridItemsColor.size()];
        for (int columnIndex = 0; columnIndex < columnColors.length; columnIndex++) {
            columnColors[columnIndex] = gridPanelGridItemsColor.get(0).get(columnIndex).value;
        }

        boolean areColumnsSolved = true;
        for (int columnIndex = 1; columnIndex < gridPanelGridItemsColor.size() && areColumnsSolved; columnIndex++) {
            for (ArrayList<GridItemColor> gridItemColors : gridPanelGridItemsColor) {
                if (columnColors[columnIndex] != gridItemColors.get(columnIndex).value) {
                    areColumnsSolved = false;
                    break;
                }
            }
        }

        return areColumnsSolved;
    }

    /**
     * Sets steps to leave when auto solve.
     *
     * @param stepsToLeave the steps to leave
     */
    void setStepsToLeaveWhenAutoSolve(int stepsToLeave) {
        stepsToLeaveWhenAutoSolve = stepsToLeave;
    }

    /**
     * Gets has cheated.
     *
     * @return the has cheated
     */
    boolean getHasCheated() {
        return hasCheated;
    }

    /**
     * Traces back a step.
     */
    private void traceBackAStep() {
        int recordIndex = stepsRecord.size() - 1;

        switch (Rotation.values()[stepsRecord.get(recordIndex).get(0)]) {
            case BACKWARD -> forwardRotateRowBy(stepsRecord.get(recordIndex).get(1),
                    stepsRecord.get(recordIndex).get(2));
            case UPWARD -> downwardRotateColumnBy(stepsRecord.get(recordIndex).get(1),
                    stepsRecord.get(recordIndex).get(2));
            case FORWARD -> backwardRotateRowBy(stepsRecord.get(recordIndex).get(1),
                    stepsRecord.get(recordIndex).get(2));
            case DOWNWARD -> upwardRotateColumnBy(stepsRecord.get(recordIndex).get(1),
                    stepsRecord.get(recordIndex).get(2));
        }

        stepsRecord.remove(recordIndex);

        gridPanel.updateGridItemColors(gridPanelGridItemsColor);
    }
}
