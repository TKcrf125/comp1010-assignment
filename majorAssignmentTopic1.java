// ===== Main.java =====
class Main {
    public static void main(String[] args) {
        GameEngine engine = new GameEngine();
        engine.startGame();
    }
}

// ===== GameEngine.java =====
class GameEngine {
    public void startGame() {
        System.out.println("Welcome to Masters of MQ!");
        // TODO: Initialize characters and start the battle
    }
}

// ===== Character.java =====
abstract class Character {
    protected String name;
    protected int health;
    protected Stats stats;
    protected StatusEffect[] statusEffects = new StatusEffect[10];

    public abstract void takeTurn(Character opponent);
    public boolean isAlive() { return health > 0; }
    public void takeDamage(int damage) {
        health -= damage;
        System.out.println(name + " took " + damage + " points of damage!");
    }
    public String getName() { return name; }
}

// ===== PlayerCharacter.java =====
class PlayerCharacter extends Character {
    public void takeTurn(Character opponent) {
        System.out.println(name + " attacks!");
        opponent.takeDamage(stats.strength);
    }
}

// ===== EnemyCharacter.java =====
class EnemyCharacter extends Character {
    public void takeTurn(Character opponent) {
        System.out.println(name + " attacks!");
        opponent.takeDamage(stats.strength);
    }
}

// ===== Action.java =====
interface Action {
    void execute(Character actor, Character target);
}

// ===== AttackAction.java =====
class AttackAction implements Action {
    public void execute(Character actor, Character target) {
        int base = actor.stats.strength;
        int bonus = DiceRoller.roll(5);
        int damage = base + bonus - target.stats.defence;
        target.takeDamage(Math.max(0, damage));
    }
}

// ===== Stats.java =====
class Stats {
    public int strength;
    public int intelligence;
    public int defence;
    public int initiative;

    public Stats(int str, int intel, int def, int init) {
        this.strength = str;
        this.intelligence = intel;
        this.defence = def;
        this.initiative = init;
    }
}

// ===== StatusEffect.java =====
class StatusEffect {
    public String name;
    public int duration;

    public StatusEffect(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }
}

// ===== DiceRoller.java =====
class DiceRoller {
    private static final java.util.Random rand = new java.util.Random();

    public static int roll(int max) {
        return rand.nextInt(max + 1);
    }
}

// ===== TextFormatter.java =====
class TextFormatter {
    public static String formatAttack(String attacker, String target, int damage) {
        return attacker + " attacked " + target + "! " + target + " took " + damage + " points of damage!";
    }
}
