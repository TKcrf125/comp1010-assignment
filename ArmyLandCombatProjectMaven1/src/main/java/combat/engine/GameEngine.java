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
// NOTE: AI-generated flow—please adjust method comments.
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
                case 1: playGame();   break;
                case 2: showTutorial(); break;
                case 3: ui.displayScores(pastScores); break;
                case 0: saveScores(); return;
            }
        }
    }

    private void showTutorial() {
        List<String> steps = List.of(
            "1. Select your unit.",
            "2. Choose action: Attack or Defend.",
            "3. Defeat all enemy units."
        );
        ui.displayTutorial(steps);
    }

    private void playGame() {
        CombatTeam team = new CombatTeam();
        EnemyTeam enemies = new EnemyTeam();
        TroopNode head = buildTroopHistory(team.getTroops());

        while (!team.isDefeated() && !enemies.isDefeated()) {
            Troop player = team.nextActive();
            Troop foe    = enemies.nextActive();
            ui.showBattleStatus(player, foe);

            int action = InputUtil.readInt("1=Attack  2=Defend: ", 1, 2);
            if (action == 1) {
                int damage = player.attack(foe);
                System.out.println("You dealt " + damage + " damage.");
            } else {
                player.defend();
                System.out.println("You brace for the next assault.");
            }

            if (foe.isAlive()) {
                int damage = foe.attack(player);
                System.out.println("Enemy dealt " + damage + " damage.");
            }
            head = head.add(player);  // recursive history

            if (team.isDefeated() || enemies.isDefeated()) break;
        }

        String result = enemies.isDefeated() ? "Victory!" : "Defeat...";
        ui.showWinner(result);
        pastScores.add(team.calculateScore());
    }

    /** Builds a simple recursive history of troop actions. */
    private TroopNode buildTroopHistory(List<Troop> troops) {
        TroopNode root = new TroopNode(null, null);
        for (Troop t : troops) {
            root = root.add(t);
        }
        return root;
    }

    /** Loads scores from disk. */
    private void loadScores() {
        File f = new File(SCORE_FILE);
        if (!f.exists()) return;
        try (BufferedReader r = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = r.readLine()) != null) {
                pastScores.add(Integer.parseInt(line));
            }
        } catch (IOException | NumberFormatException e) {
            // NOTE: you may want to log or handle more gracefully
        }
    }

    /** Saves scores to disk on exit. */
    private void saveScores() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(SCORE_FILE))) {
            for (int s : pastScores) pw.println(s);
        } catch (IOException e) {
            // NOTE: you may want to log or notify user
        }
    }
}
