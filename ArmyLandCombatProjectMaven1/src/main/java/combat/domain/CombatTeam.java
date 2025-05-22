package combat.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * A team of player-controlled combat units.
 */
// NOTE: AI-generated—please rephrase and expand on choice logic.
public class CombatTeam {
    private final List<Troop> troops = new ArrayList<>();
    private int currentIndex = 0;

    public CombatTeam() {
        troops.add(new Troop("Infantry Squad", 60, 20, 10));
        troops.add(new Troop("Armored Platoon", 80, 30, 20));
        troops.add(new Troop("Artillery Battery", 50, 40, 5));
    }

    public Troop nextActive() {
        while (!troops.get(currentIndex).isAlive()) {
            currentIndex = (currentIndex + 1) % troops.size();
        }
        return troops.get(currentIndex);
    }

    public boolean isDefeated() {
        return troops.stream().noneMatch(Troop::isAlive);
    }

    public List<Troop> getTroops() {
        return List.copyOf(troops);
    }

    public int calculateScore() {
        return troops.stream().mapToInt(Troop::calculateScore).sum();
    }
}
