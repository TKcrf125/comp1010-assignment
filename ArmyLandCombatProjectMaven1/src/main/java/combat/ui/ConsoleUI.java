package combat.ui;

import combat.util.InputUtil;
import combat.domain.Troop;

import java.util.List;

/**
 * Handles all console printing and menus.
 */
public class ConsoleUI {

    /** Main menu display */
    public void showMainMenu() {
        System.out.println("=== Army Land Combat ===");
        System.out.println("1. Play");
        System.out.println("2. Tutorial");
        System.out.println("3. Past Scores");
        System.out.println("0. Exit");
    }

    /** Prompt for main menu choice */
    public int promptMenuChoice() {
        return InputUtil.readInt("Enter choice (0–3): ", 0, 3);
    }

    /** Displays the tutorial submenu */
    public void showTutorialMenu(List<String> steps) {
        System.out.println("\n--- Tutorial ---");
        for (int i = 0; i < steps.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, steps.get(i));
        }
        System.out.println("0. Back");
    }

    /** Prompt for tutorial choice */
    public int promptTutorialChoice(int maxOption) {
        return InputUtil.readInt("Select tutorial step (0–" + maxOption + "): ", 0, maxOption);
    }

    /** Displays a single tutorial step */
    public void displayTutorialStep(String step) {
        System.out.println("\n" + step);
        InputUtil.waitForEnter();
    }

    /** Displays past scores */
    public void displayScores(List<Integer> scores) {
        System.out.println("\n--- Past Scores ---");
        if (scores.isEmpty()) {
            System.out.println("No scores recorded.");
        } else {
            for (int s : scores) {
                System.out.println("Score: " + s);
            }
        }
        InputUtil.waitForEnter();
    }

    /** Displays the current battle status */
    public void showBattleStatus(Troop active, Troop enemy) {
        System.out.printf("%nYou: %s  |  Enemy: %s%n",
            active.getStatus(), enemy.getStatus());
    }

    /** Displays the final winner message */
    public void showWinner(String message) {
        System.out.println("\n" + message);
        InputUtil.waitForEnter();
    }
}
