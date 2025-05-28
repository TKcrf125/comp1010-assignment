package combat.ui;

import combat.util.InputUtil;
import combat.domain.Troop;

import java.util.List;

/**
 * Handles all console printing and menus. AKA the User Interface 
 */
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

    public void showTutorialMenu(List<String> steps) {
        System.out.println("\n--- Tutorial ---");
        for (int i = 0; i < steps.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, steps.get(i));
        }
        System.out.println("0. Back");
    }

    public int promptTutorialChoice(int maxOption) {
        return InputUtil.readInt("Select tutorial step (0–" + maxOption + "): ", 0, maxOption);
    }

    public void displayTutorialStep(String step) {
        System.out.println("\n" + step);
        InputUtil.waitForEnter();
    }

    public void showBattleStatus(Troop active, Troop enemy) {
        System.out.printf("%nYou: %s  |  Enemy: %s%n",
            active.toString(), enemy.toString());
    }

    public void showBattleOptions(boolean canUseAbility) {
        System.out.println("\nChoose your action:");
        System.out.println("1. Attack");
        System.out.println("2. Defend");
        if (canUseAbility) {
            System.out.println("3. Use Special Ability");
        }
    }

    public int promptBattleAction(int maxOption) {
        return InputUtil.readInt("Enter choice (1–" + maxOption + "): ", 1, maxOption);
    }

    public void showWinner(String message) {
        System.out.println("\n" + message);
        InputUtil.waitForEnter();
    }
}
