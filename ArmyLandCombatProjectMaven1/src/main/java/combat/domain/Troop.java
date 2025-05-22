package combat.domain;

/**
 * Represents a single combat unit.
 */
// NOTE: AI-generated class—please reword field/method comments.
public class Troop {
    private final String name;
    private int health;
    private final int attackPower;
    private final int defencePower;

    public Troop(String name, int health, int attackPower, int defencePower) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.defencePower = defencePower;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int attack(Troop other) {
        int dmg = Math.max(0, attackPower - other.defencePower);
        other.takeDamage(dmg);
        return dmg;
    }

    public void defend() {
        // simple defend: regain some health
        this.health += defencePower / 2;
    }

    public void takeDamage(int dmg) {
        this.health = Math.max(0, health - dmg);
    }

    public int calculateScore() {
        return health + attackPower + defencePower;
    }

    public String getStatus() {
        return String.format("%s [HP:%d]", name, health);
    }
}
