package main.java.game.grid;

/**
 * The enum Grid order.
 */
public enum GridOrder {
    /**
     * Two grid order.
     */
    TWO(2),
    /**
     * Four grid order.
     */
    FOUR(4),
    /**
     * Six grid order.
     */
    SIX(6);

    /**
     * The Value.
     */
    public final int value;

    GridOrder(int value) {
        this.value = value;
    }
}
