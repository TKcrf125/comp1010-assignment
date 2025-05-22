package combat.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * A team of opposing combat units.
 */
// NOTE: AI-generated—please rephrase and add variety.
public class EnemyTeam {
    private final List<Troop> troops = new ArrayList<>();
    private int currentIndex = 0;

    public EnemyTeam() {
        troops.add(new Troop("Infantry Unit", 55, 18, 8));
        troops.add(new Troop("Armored Division", 90, 35, 25));
        troops.add(new Troop("Artillery Regiment", 45, 45, 5));
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
}
