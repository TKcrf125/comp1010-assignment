package combat.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * the enemy's troops .
 */
public class EnemyTeam {
    private final List<Troop> troops = new ArrayList<>();
    private int currentIndex = 0;

    public EnemyTeam() {
        troops.add(new Troop(
            "Enemy Infantry", 55, 18, 8,
            0.08, 2.0,                     // 8% crit
            new Ability("None", "No ability", 0)
        ));
        troops.add(new Troop(
            "Enemy Armor", 90, 35, 25,
            0.05, 2.2,                    //5% ctit
            new Ability("None", "No ability", 0)
        ));
        troops.add(new Troop(
            "Enemy Artillery", 45, 45, 5,
            0.12, 1.7,                  //12% crit (artilery goated)
            new Ability("None", "No ability", 0)
        ));
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
}
