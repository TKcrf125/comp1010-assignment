// File: src/main/java/combat/domain/Ability.java
package combat.domain;

/**
 * Represents a special action a unit can perform, with a cooldown.
 */
public class Ability {
    private final String name;
    private final int cooldownTurns;
    private int turnsUntilReady;

    public Ability(String name, String description, int cooldownTurns) {
        this.name = name;
        this.cooldownTurns = cooldownTurns;
        this.turnsUntilReady = 0;
    }

    public String getName() {
        return name;
    }

    public boolean isReady() {
        return turnsUntilReady == 0;
    }

    public void use() {
        if (!isReady()) {
            throw new IllegalStateException(name + " is not ready");
        }
        turnsUntilReady = cooldownTurns;
    }

    public void tick() {
        if (turnsUntilReady > 0) {
            turnsUntilReady--;
        }
    }

    /** Execute the ability’s effect and return a descriptive message. */
    public String apply(Troop user, Troop target) {
        int base = Math.max(0, user.getAttackPower() - target.getDefencePower());
        int damage = base * 2;
        target.takeDamage(damage);
        return user.getName() + " uses " + name + " for " + damage + " damage!";
    }
}

