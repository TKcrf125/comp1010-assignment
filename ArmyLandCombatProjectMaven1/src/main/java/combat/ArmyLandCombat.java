package combat;

import combat.engine.GameEngine;
import combat.ui.ConsoleUI;

/**
 * Entry point for the Army Land Combat application.
 */
// NOTE: AI-generated class/javadoc—please rephrase in your own words.
public class ArmyLandCombat {
    public static void main(String[] args) {
        ConsoleUI ui = new ConsoleUI();        // NOTE: UI instantiation
        GameEngine engine = new GameEngine(ui);
        engine.start();                         // NOTE: Starts main game loop
    }
}
