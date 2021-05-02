package main.java.menu;

/**
 * The enum Main menu element text.
 */
enum MainMenuElementText {
    /**
     * Menu main menu element text.
     */
    MENU("Menu"),
    /**
     * The New game text.
     */
    NEW_GAME("New Game"),
    /**
     * The Individual new game text.
     */
    INDIVIDUAL_NEW_GAME("%d x %d"),
    /**
     * The Quit tex.
     */
    QUIT("Quit Game");

    final String value;

    MainMenuElementText(String value) {
        this.value = value;
    }
}
