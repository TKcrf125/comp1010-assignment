package combat.ui;

import combat.util.InputUtil;
import combat.domain.Troop;

import java.util.List;

/**
 * Handles all console printing and menus.
 */
// NOTE: AI-generated structure—please reword menu descriptions.
public class ConsoleUI {
    public void showMainMenu() {
        System.out.println("=== Army Land Combat ===");
        System.out.println("1. Play");
        System.out.println("2. Tutorial");
        System.out.println("3. Past Scores");
        System.out.println("0. Exit");
    }

    public int promptMenuChoice() {
        return InputUtil.readInt("Enter choice (0–3): ", 0, 3);
    }

    public void displayTutorial(List<String> steps) {
        System.out.println("--- Tutorial ---");
        for (String step : steps) {
            System.out.println(step);
        }
        InputUtil.waitForEnter();
    }

    public void displayScores(List<Integer> scores) {
        System.out.println("--- Past Scores ---");
        if (scores.isEmpty()) {
            System.out.println("No scores recorded.");
        } else {
            scores.forEach(s -> System.out.println("Score: " + s));
        }
        InputUtil.waitForEnter();
    }

    public void showBattleStatus(Troop active, Troop enemy) {
        System.out.printf("You: %s  |  Enemy: %s%n",
            active.getStatus(), enemy.getStatus());
    }

    public void showWinner(String message) {
        System.out.println(message);
        InputUtil.waitForEnter();
    }
}
