package main.java.game.grid;

/**
 * The enum Rotation.
 */
enum Rotation {
    /**
     * Backward rotation.
     */
    BACKWARD(0),
    /**
     * Upward rotation.
     */
    UPWARD(1),
    /**
     * Forward rotation.
     */
    FORWARD(2),
    /**
     * Downward rotation.
     */
    DOWNWARD(3);

    /**
     * The Value.
     */
    final int value;

    Rotation(int value) {
        this.value = value;
    }
}
