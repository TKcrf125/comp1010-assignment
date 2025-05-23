package combat.engine;

import combat.domain.CombatTeam;
import combat.domain.EnemyTeam;
import combat.domain.Troop;
import combat.domain.TroopNode;
import combat.ui.ConsoleUI;
import combat.util.InputUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Core game logic and flow control.
 */
public class GameEngine {
    private final ConsoleUI ui;
    private final List<Integer> pastScores = new ArrayList<>();
    private static final String SCORE_FILE = "scores.txt";

    public GameEngine(ConsoleUI ui) {
        this.ui = ui;
        loadScores();
    }

    /** Starts the main loop. */
    public void start() {
        while (true) {
            ui.showMainMenu();
            switch (ui.promptMenuChoice()) {
                case 1:
                    playGame();
                    break;
                case 2:
                    showTutorial();
                    break;
                case 3:
                    ui.displayScores(pastScores);
                    break;
                case 0:
                    saveScores();
                    return;
            }
        }
    }

    /** Interactive tutorial: choose a step or go back. */
    private void showTutorial() {
        List<String> steps = List.of(
            "Step 1: Select your unit and enter the battlefield.",
            "Step 2: Each turn, choose 'Attack' to damage the enemy or 'Defend' to brace.",
            "Step 3: Reduce all enemy unit health to zero to win."
        );
        while (true) {
            ui.showTutorialMenu(steps);
            int choice = ui.promptTutorialChoice(steps.size());
            if (choice == 0) {
                break;
            } else {
                ui.displayTutorialStep(steps.get(choice - 1));
            }
        }
    }

    /** Main gameplay loop */
    private void playGame() {
        CombatTeam team   = new CombatTeam();
        EnemyTeam enemies = new EnemyTeam();
        TroopNode history = buildTroopHistory(team.getTroops());

        while (!team.isDefeated() && !enemies.isDefeated()) {
            Troop player = team.nextActive();
            Troop foe    = enemies.nextActive();
            ui.showBattleStatus(player, foe);

            int action = InputUtil.readInt("1=Attack  2=Defend: ", 1, 2);
            if (action == 1) {
                int dmg = player.attack(foe);
                System.out.println("You dealt " + dmg + " damage.");
            } else {
                player.defend();
                System.out.println("You brace for the next assault.");
            }

            if (foe.isAlive()) {
                int dmg = foe.attack(player);
                System.out.println("Enemy dealt " + dmg + " damage.");
            }
            history = history.add(player);

            if (team.isDefeated() || enemies.isDefeated()) {
                break;
            }
        }

        String result = enemies.isDefeated() ? "Victory!" : "Defeat...";
        ui.showWinner(result);
        pastScores.add(team.calculateScore());
    }

    /** Builds a recursive history of all troops. */
    private TroopNode buildTroopHistory(List<Troop> troops) {
        TroopNode root = new TroopNode(null, null);
        for (Troop t : troops) {
            root = root.add(t);
        }
        return root;
    }

    /** Loads scores from disk. */
    private void loadScores() {
        File file = new File(SCORE_FILE);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                pastScores.add(Integer.parseInt(line));
            }
        } catch (IOException | NumberFormatException e) {
            // handle or log as needed
        }
    }

    /** Saves scores to disk on exit. */
    private void saveScores() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SCORE_FILE))) {
            for (int score : pastScores) {
                writer.println(score);
            }
        } catch (IOException e) {
            // handle or log as needed
        }
    }
}
