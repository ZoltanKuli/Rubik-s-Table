package main.java.game.grid;

import java.awt.*;

/**
 * The enum Grid item color.
 */
enum GridItemColor {
    /**
     * The Yellow.
     */
    YELLOW(new Color(255, 220, 0)),
    /**
     * The Orange.
     */
    ORANGE(new Color(255, 132, 24)),
    /**
     * The Blue.
     */
    BLUE(new Color(0, 127, 238)),
    /**
     * The Green.
     */
    GREEN(new Color(46, 204, 64)),
    /**
     * The White.
     */
    WHITE(new Color(240, 240, 240)),
    /**
     * The Red.
     */
    RED(new Color(255, 50, 40));

    /**
     * The Value.
     */
    final Color value;

    GridItemColor(Color value) {
        this.value = value;
    }

    /**
     * Gets matching grid item color.
     *
     * @param color the color
     * @return the matching grid item color
     */
    static GridItemColor getMatchingGridItemColor(Color color) {
        GridItemColor[] gridItemColors = GridItemColor.values();
        for (GridItemColor gridItemColor : gridItemColors) {
            if (color == gridItemColor.value) {
                return gridItemColor;
            }
        }

        return null;
    }
}
