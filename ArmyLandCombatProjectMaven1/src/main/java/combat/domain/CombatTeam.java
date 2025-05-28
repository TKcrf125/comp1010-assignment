package combat.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Players commandable troops.
 */
public class CombatTeam {
    private final List<Troop> troops = new ArrayList<>();
    private int currentIndex = 0;

    public CombatTeam() {
        troops.add(new Troop(
            "Infantry Squad", 60, 20, 10,
            0.10, 2.0,                     // 10% crit, 2× multiplier
            new Ability("First Aid", "Heal 20 HP", 3)
        ));
        troops.add(new Troop(
            "Armored Platoon", 80, 30, 20,
            0.05, 2.5,                     // 5% crit, 2.5× multiplier
            new Ability("Shield Wall", "Boost defense next turn", 2)
        ));
        troops.add(new Troop(
            "Artillery Battery", 50, 40, 5,
            0.15, 1.8,                     // 15% crit, 1.8× multiplier
            new Ability("Barrage", "Damage all enemies", 4)
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
        return List.copyOf(troops);      // Return the copy
    }

    public int calculateScore() {
        return troops.stream()
                     .mapToInt(t -> t.getAttackPower() 
                                + t.getDefencePower() 
                                + t.toString().length())
                     .sum();                    // this is the scoring formula
    }
}

