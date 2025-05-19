import java.util.List;
import java.util.ArrayList;

public class ArmyLandCombat {

    public static void main(String[] args) {
        showMenu();
    }

    /**
     * Main menu loop
     */
    public static void showMenu() {
        while (true) {
            // Display ASCII art
            System.out.println("             \\ | //");
            System.out.println("              \\|//");
            System.out.println("           ---  *  ---");
            System.out.println("              //|\\");
            System.out.println("             // | \\");
            System.out.println();
            // Menu options
            System.out.println("=== Army Land Combat ===");
            System.out.println("1. Play");
            System.out.println("2. Tutorial");
            System.out.println("3. Past Scores");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = readMenuChoice();
            switch (choice) {
                case 1:
                    playGame();
                    break;
                case 2:
                    showTutorial();
                    break;
                case 3:
                    showPastScores();
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice; please enter 0–3.");
            }
        }
    }

    /**
     * Reads a single digit from System.in, discards rest of line
     */
    private static int readMenuChoice() {
        int raw = -1;
        try {
            raw = System.in.read();
            int c;
            do { c = System.in.read(); } while (c != -1 && c != '\n' && c != '\r');
        } catch (Exception e) {
            // ignore
        }
        return (raw >= '0' && raw <= '9') ? raw - '0' : -1;
    }

    /**
     * Waits for the user to press Enter
     */
    private static void waitForEnter() {
        try {
            int c;
            do { c = System.in.read(); } while (c != -1 && c != '\n');
        } catch (Exception e) {
            // ignore
        }
    }

    /**
     * Troop selection menu invoked when 'Play' is chosen
     */
    public static void playGame() {
        while (true) {
            System.out.println();
            System.out.println("--- Troop Selection ---");
            // List corps
            CorpsType[] corps = CorpsType.values();
            for (int i = 0; i < corps.length; i++) {
                System.out.printf("%d. %s\n", i + 1, corps[i]);
            }
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose a corps: ");

            int cChoice = readMenuChoice();
            if (cChoice == 0) return;
            if (cChoice < 1 || cChoice > corps.length) {
                System.out.println("Invalid selection.");
                continue;
            }
            CorpsType selectedCorps = corps[cChoice - 1];

            // List troops in selected corps
            List<Troop> troopList = Troops.getTroopsByCorps(selectedCorps);
            while (true) {
                System.out.println();
                System.out.println("--- " + selectedCorps + " Units ---");
                for (int j = 0; j < troopList.size(); j++) {
                    Troop t = troopList.get(j);
                    System.out.printf("%d. %s (FP:%d AR:%d CM:%d RG:%d CMD:%d)\n", j + 1,
                        t.getRole(), t.getFirepower(), t.getArmour(), t.getCamouflage(), t.getRange(), t.getCommand());
                }
                System.out.println("0. Back to Corps Selection");
                System.out.print("Choose a unit: ");

                int tChoice = readMenuChoice();
                if (tChoice == 0) break;
                if (tChoice < 1 || tChoice > troopList.size()) {
                    System.out.println("Invalid selection.");
                    continue;
                }
                Troop chosen = troopList.get(tChoice - 1);
                System.out.println();
                System.out.println("Selected: " + chosen.getRole() + " [" + chosen.getCorps() + "]");
                System.out.println("Firepower: " + chosen.getFirepower());
                System.out.println("Armour:    " + chosen.getArmour());
                System.out.println("Camouflage:" + chosen.getCamouflage());
                System.out.println("Range:     " + chosen.getRange());
                System.out.println("Command:   " + chosen.getCommand());
                System.out.println("Abilities: " + chosen.getSpecialAbilities());
                System.out.println();
                System.out.println("Press Enter to return to Main Menu...");
                waitForEnter();
                return;
            }
        }
    }

    /**
     * Shows tutorial text and returns to menu on Enter
     */
    public static void showTutorial() {
        System.out.println();
        System.out.println("[TUTORIAL]");
        System.out.println("Welcome to Army Land Combat!");
        System.out.println("– Use WASD to move your tank.");
        System.out.println("– Press SPACE to fire.");
        System.out.println("– Defeat all enemy units to win.");
        System.out.println("– Collect health packs to restore HP.");
        System.out.println();
        System.out.println("Press Enter to return to Main Menu...");
        waitForEnter();
    }

    /**
     * Displays past scores and returns to menu on Enter
     */
    public static void showPastScores() {
        System.out.println();
        System.out.println("[PAST SCORES]");
        // TODO: load actual scores
        System.out.println("Alice: 12,340");
        System.out.println("Bob:   8,920");
        System.out.println("Carol: 5,750");
        System.out.println();
        System.out.println("Press Enter to return to Main Menu...");
        waitForEnter();
    }
}

/**
 * Enumeration of corps categories
 */
enum CorpsType {
    INFANTRY,
    ARTILLERY,
    ARMOURED,
    ENGINEERS,
    MEDICAL,
    TRANSPORT,
    AVIATION;
}

/**
 * Enumeration of soldier roles within infantry
 */
enum Role {
    INFANTRY,
    MARKSMAN,
    GUNNER,
    GRENADIER,
    FIRE_TEAM_LEADER;
}

/**
 * Represents a single troop unit with attributes and abilities
 */
class Troop {
    private CorpsType corps;
    private Role role;
    private int firepower;
    private int armour;
    private int command;
    private int camouflage;
    private int range;
    private List<String> specialAbilities;

    public Troop(CorpsType corps, Role role, int firepower, int armour, int command, int camouflage, int range, List<String> specialAbilities) {
        this.corps = corps;
        this.role = role;
        this.firepower = firepower;
        this.armour = armour;
        this.command = command;
        this.camouflage = camouflage;
        this.range = range;
        this.specialAbilities = specialAbilities;
    }

    public CorpsType getCorps() { return corps; }
    public Role getRole() { return role; }
    public int getFirepower() { return firepower; }
    public int getArmour() { return armour; }
    public int getCommand() { return command; }
    public int getCamouflage() { return camouflage; }
    public int getRange() { return range; }
    public List<String> getSpecialAbilities() { return specialAbilities; }
}

/**
 * Database of all possible troop units
 */
class Troops {
    private static List<Troop> troopsDatabase = new ArrayList<>();

    static {
        // Infantry units
        troopsDatabase.add(new Troop(CorpsType.INFANTRY, Role.INFANTRY, 50, 30, 40, 60, 70, List.of("Quick March")));
        troopsDatabase.add(new Troop(CorpsType.INFANTRY, Role.MARKSMAN, 80, 20, 30, 50, 90, List.of("Stealth Shot")));
        troopsDatabase.add(new Troop(CorpsType.INFANTRY, Role.GUNNER, 90, 40, 30, 30, 80, List.of("Suppressive Fire")));
        troopsDatabase.add(new Troop(CorpsType.INFANTRY, Role.GRENADIER, 85, 35, 30, 40, 60, List.of("Fragmentation")));
        troopsDatabase.add(new Troop(CorpsType.INFANTRY, Role.FIRE_TEAM_LEADER, 60, 40, 80, 50, 70, List.of("Inspire")));
        // TODO: add units for ARTILLERY, ARMOURED, ENGINEERS, MEDICAL, TRANSPORT, AVIATION
    }

    /**
     * Returns all troops
     */
    public static List<Troop> getAllTroops() {
        return troopsDatabase;
    }

    /**
     * Returns troops filtered by corps
     */
    public static List<Troop> getTroopsByCorps(CorpsType corps) {
        List<Troop> list = new ArrayList<>();
        for (Troop t : troopsDatabase) {
            if (t.getCorps() == corps) {
                list.add(t);
            }
        }
        return list;
    }

    /**
     * Returns troops filtered by role
     */
    public static List<Troop> getTroopsByRole(Role role) {
        List<Troop> list = new ArrayList<>();
        for (Troop t : troopsDatabase) {
            if (t.getRole() == role) {
                list.add(t);
            }
        }
        return list;
    }
}
