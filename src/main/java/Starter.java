package main.java;

import com.formdev.flatlaf.FlatIntelliJLaf;

/**
 * The type Starter.
 */
public class Starter {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        FlatIntelliJLaf.install();

        new MainFrame();
    }
}
