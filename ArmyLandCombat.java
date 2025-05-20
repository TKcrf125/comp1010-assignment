import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Corps categories
enum CorpsType { INFANTRY, ARTILLERY, ARMOURED, ENGINEERS, MEDICAL, TRANSPORT, AVIATION }
// Soldier roles
enum Role { INFANTRY, MARKSMAN, GUNNER, GRENADIER, FIRE_TEAM_LEADER }

class Troop {
    private int firepower;
    private int armour;
    private final int baseArmour;
    private int camouflage;
    private int range;
    private boolean alive = true;
    
    public Troop(int fp, int ar, int cam, int rg) {
        this.firepower = fp;
        this.armour = ar;
        this.baseArmour = ar;
        this.camouflage = cam;
        this.range = rg;
    }
    public Role getRole() { return null; } // role unused in this snippet
    public int getFirepower() { return firepower; }
    public int getArmour() { return armour; }
    public int getCamouflage() { return camouflage; }
    public int getRange() { return range; }
    public boolean isAlive() { return alive; }
    
    public void takeDamage(int damage) {
        armour -= damage;
        if (armour <= 0) {
            alive = false;
            armour = 0;
        }
    }
    public void healArmor(int amount) {
        armour = Math.min(baseArmour, armour + amount);
    }
}

public class ArmyLandCombat {
    public static void main(String[] args) {
        showMenu();
    }

    public static void showMenu() {
        while (true) {
            System.out.println("=== Army Land Combat ===");
            System.out.println("1. Play");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            int choice = readInt();
            if (choice == 1) playGame();
            else if (choice == 0) return;
            else System.out.println("Invalid choice.");
        }
    }

    private static int readInt() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return Integer.parseInt(reader.readLine().trim());
        } catch (Exception e) {
            return -1;
        }
    }

    private static void waitForEnter() {
        try { System.in.read(); } catch (Exception e) { }
    }

    public static void playGame() {
        System.out.println("Contact front! Range 100 meters, indication axis of advance, enemy section moving on us!");
        System.out.println("Press Enter to engage...");
        waitForEnter();

        CombatTeam player = new CombatTeam();
        EnemyTeam enemy = new EnemyTeam();
        int distance = 100;

        while (player.hasAlive() && enemy.hasAlive()) {
            for (Troop t : player.getAlive()) {
                int actions = 2;
                while (actions-- > 0 && player.hasAlive() && enemy.hasAlive()) {
                    System.out.println("\nAction for troop. Distance: " + distance);
                    System.out.println("1.Shoot 2.Move 3.Medicate 4.Show Team");
                    System.out.print("Action: ");
                    switch (readInt()) {
                        case 1: distance = playerShoot(t, enemy, distance); break;
                        case 2: distance = playerMove(distance); break;
                        case 3: playerMedicate(t); break;
                        case 4: player.showTeam(); actions++; break;
                        default: System.out.println("Invalid"); actions++; break;
                    }
                }
            }
            player.reinforce();
            if (!enemy.hasAlive()) break;

            System.out.println("\n--- ENEMY TURN ---");
            for (Troop t : enemy.getAlive()) {
                int actions = 2;
                while (actions-- > 0 && player.hasAlive()) {
                    enemy.randomAction(t, player);
                }
            }
            enemy.reinforce();
        }
        System.out.println(player.hasAlive() ? "You win!" : "You lose.");
    }

    private static int playerShoot(Troop shooter, EnemyTeam enemy, int distance) {
        if (distance > shooter.getRange()) {
            System.out.println("Target out of range.");
            return distance;
        }
        List<Troop> targets = enemy.getAlive();
        for (int i = 0; i < targets.size(); i++) {
            Troop e = targets.get(i);
            System.out.printf("%d: AR:%d CM:%d\n", i+1, e.getArmour(), e.getCamouflage());
        }
        System.out.print("Select target: ");
        int idx = readInt() - 1;
        if (idx < 0 || idx >= targets.size()) {
            System.out.println("Invalid");
            return distance;
        }
        Troop e = targets.get(idx);
        int roll = (int)(Math.random() * 6) + 1;
        int atk = roll * shooter.getFirepower();
        int def = roll * e.getCamouflage();
        System.out.println("Roll=" + roll + " ATK=" + atk + " DEF=" + def);
        if (atk > def) {
            int dmg = atk - def;
            e.takeDamage(dmg);
            System.out.println("Damage=" + dmg + "; AR now " + e.getArmour());
            if (!e.isAlive()) System.out.println("Target neutralized.");
        } else {
            System.out.println("Shot failed.");
        }
        return distance;
    }

    private static int playerMove(int distance) {
        System.out.println("1.Close 20m 2.Withdraw 20m");
        System.out.print("Choice: ");
        if (readInt() == 1) distance = Math.max(0, distance - 20);
        else distance += 20;
        System.out.println("Distance=" + distance + "m");
        return distance;
    }

    private static void playerMedicate(Troop t) {
        int before = t.getArmour();
        t.healArmor(2);
        System.out.println("Armor " + before + "->" + t.getArmour());
    }
}

class CombatTeam {
    private List<Troop> troops = new ArrayList<>(Arrays.asList(
        new Troop(80,20,50,90),
        new Troop(90,40,30,80),
        new Troop(85,35,40,60),
        new Troop(60,40,50,70)
    ));
    public List<Troop> getAlive() {
        List<Troop> a = new ArrayList<>();
        for (Troop t : troops) if (t.isAlive()) a.add(t);
        return a;
    }
    public boolean hasAlive() { return !getAlive().isEmpty(); }
    public void showTeam() {
        System.out.println("-- Player Team --");
        for (Troop t : getAlive()) System.out.println("AR:" + t.getArmour());
    }
    public void reinforce() { System.out.println("(Reinforcements placeholder)"); }
    public void takeRandomDamage(Troop attacker) {}
}

class EnemyTeam {
    private List<Troop> troops = new ArrayList<>();
    public EnemyTeam() {
        for (int i = 0; i < 3; i++) troops.add(new Troop(80,20,50,90));
        troops.add(new Troop(90,40,30,80));
        troops.add(new Troop(85,35,40,60));
        troops.add(new Troop(60,40,50,70));
    }
    public List<Troop> getAlive() {
        List<Troop> a = new ArrayList<>();
        for (Troop t : troops) if (t.isAlive()) a.add(t);
        return a;
    }
    public boolean hasAlive() { return !getAlive().isEmpty(); }
    public void randomAction(Troop t, CombatTeam player) {
        // Implement enemy behavior
    }
    public void reinforce() { System.out.println("(Enemy reinforcements)"); }
}

