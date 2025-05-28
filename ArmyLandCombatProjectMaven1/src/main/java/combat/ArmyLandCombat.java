package majorAssignmentTopic1.src.main.java.combat;

import combat.engine.GameEngine;
import combat.ui.ConsoleUI;

/**
 * this is the main bit of code that brings up the menue and starts the game .
 */
public class ArmyLandCombat {
    public static void main(String[] args) {
        ConsoleUI ui = new ConsoleUI();        // UI instantiation
        GameEngine engine = new GameEngine(ui);
        engine.start();                         // Starts the game
    }
}
