package majorAssignmentTopic1.src.main.java.combat.domain;

import java.util.Random;

/**
 * a combat unit's health, stats, crits, and an ability.
 */
public class Troop {
    private final String name;
    private int health;
    private final int attackPower;
    private final int defencePower;
    private final double critChance;
    private final double critMultiplier;
    private final Ability ability;
    private final Random rng = new Random();

    public Troop(String name,
                 int health,
                 int attackPower,
                 int defencePower,
                 double critChance,
                 double critMultiplier,
                 Ability ability) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.defencePower = defencePower;
        this.critChance = critChance;
        this.critMultiplier = critMultiplier;
        this.ability = ability;
    }

    public String getName() {
        return name;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getDefencePower() {
        return defencePower;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void takeDamage(int dmg) {
        this.health = Math.max(0, health - dmg);
    }

    /** attack with a chance for a critical hit. */
    public int attack(Troop other) {
        int base = Math.max(0, attackPower - other.defencePower);
        boolean isCrit = rng.nextDouble() < critChance;
        int damage = isCrit
            ? (int)(base * critMultiplier)
            : base;
        if (isCrit) {
            System.out.println("** Critical hit by " + name + "! **");
        }
        other.takeDamage(damage);
        return damage;
    }

    /** defending */
    public void defend() {
        this.health += defencePower / 2;
    }

    public Ability getAbility() {
        return ability;
    }

    /** Use special ability */
    public String useAbility(Troop target) {
        if (!ability.isReady()) {
            return ability.getName() + " is not ready.";
        }
        ability.use();
        return ability.apply(this, target);
    }

    /** ability cooldown */
    public void tickAbility() {
        ability.tick();
    }

    @Override
    public String toString() {
        return String.format("%s [HP:%d]", name, health);
    }
}
